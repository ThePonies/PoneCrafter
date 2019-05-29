package org.theponies.ponecrafter.component

import org.theponies.ponecrafter.util.importer.ObjImporter
import javafx.scene.*
import javafx.scene.paint.Color
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import org.theponies.ponecrafter.model.MeshData

class ModelViewer(width: Int, height: Int, backgroundColor: Color) : SubScene(Group(), width.toDouble(), height.toDouble(), true, SceneAntialiasing.BALANCED) {
    private val cameraYRotate = Rotate(0.0, Rotate.Y_AXIS)
    private var model: Node? = null
    private val root = getRoot() as Group

    init {
        val camera = PerspectiveCamera(true)
        val cameraLookXRotate = Rotate(-30.0, Rotate.X_AXIS)
        val cameraLookZRotate = Rotate(0.0, Rotate.Z_AXIS)
        val cameraPosition = Translate(0.0, -4.0, -7.0)
        fill = backgroundColor
        camera.transforms.addAll(cameraYRotate, cameraPosition, cameraLookXRotate, cameraLookZRotate)
        camera.nearClip = 0.1
        camera.farClip = 100.0
        setCamera(camera)
        root.children.add(camera)
    }

    fun rotateY(amount: Number) {
        cameraYRotate.angle += amount.toDouble()
    }

    fun loadModel(meshData: MeshData) {
        root.children.remove(model)
        ObjImporter().importModel(meshData).let {
            it.transforms.add(Rotate(180.0, Rotate.Z_AXIS))
            model = it
        }
        root.children.addAll(model)
    }
}
