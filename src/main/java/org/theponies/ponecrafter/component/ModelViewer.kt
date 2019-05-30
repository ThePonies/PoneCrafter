package org.theponies.ponecrafter.component

import javafx.beans.value.ObservableValue
import javafx.scene.*
import javafx.scene.image.Image
import javafx.scene.paint.Material
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.MeshView
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import org.theponies.ponecrafter.model.ImageData
import org.theponies.ponecrafter.model.MeshData
import org.theponies.ponecrafter.util.importer.ObjImporter

class ModelViewer(
    width: Int,
    height: Int,
    observableMeshData: ObservableValue<MeshData?>,
    observableTexture: ObservableValue<ImageData?>,
    op: ModelViewer.() -> Unit = {}
) : SubScene(Group(), width.toDouble(), height.toDouble(), true, SceneAntialiasing.BALANCED) {
    private val cameraYRotate = Rotate(0.0, Rotate.Y_AXIS)
    private var model: Node? = null
    private var meshView: MeshView? = null
    private var material: Material? = null
    private val root = getRoot() as Group

    init {
        val camera = PerspectiveCamera(true)
        val cameraLookXRotate = Rotate(-30.0, Rotate.X_AXIS)
        val cameraLookZRotate = Rotate(0.0, Rotate.Z_AXIS)
        val cameraPosition = Translate(0.0, -4.0, -7.0)
        camera.transforms.addAll(cameraYRotate, cameraPosition, cameraLookXRotate, cameraLookZRotate)
        camera.nearClip = 0.1
        camera.farClip = 100.0
        setCamera(camera)
        root.children.add(camera)
        observableMeshData.addListener { _, _, newValue -> if (newValue != null) loadModel(newValue) }
        observableTexture.addListener { _, _, newValue -> if (newValue != null) setTexture(newValue) }
        op()
    }

    fun rotateY(amount: Number) {
        cameraYRotate.angle += amount.toDouble()
    }

    private fun loadModel(meshData: MeshData): Boolean {
        root.children.remove(model)
        val meshView = ObjImporter().importModel(meshData)
        if (meshView != null) {
            val model = Group(meshView)
            model.transforms.add(Rotate(180.0, Rotate.Z_AXIS))
            root.children.addAll(model)
            this.model = model
            this.meshView = meshView
            updateMaterial()
            return true
        }
        return false
    }

    private fun setTexture(imageData: ImageData) {
        val texture = Image(imageData.data.inputStream())
        val material = PhongMaterial()
        material.diffuseMap = texture
        this.material = material
        updateMaterial()
    }

    private fun updateMaterial() {
        material?.let { meshView?.material = it }
    }
}
