package org.theponies.ponecrafter.component

import javafx.beans.InvalidationListener
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.*
import javafx.scene.image.Image
import javafx.scene.paint.Material
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.MeshView
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import org.theponies.ponecrafter.model.ImageData
import org.theponies.ponecrafter.model.MeshData
import org.theponies.ponecrafter.model.Vector2
import org.theponies.ponecrafter.util.importer.ObjImporter
import tornadofx.add

class ModelViewer(
    width: Int,
    height: Int,
    observableMeshData: ObservableValue<MeshData?>,
    observableTexture: ObservableValue<ImageData?>,
    observableTiles: ObservableList<Vector2>,
    op: ModelViewer.() -> Unit = {}
) : SubScene(Group(), width.toDouble(), height.toDouble(), true, SceneAntialiasing.BALANCED) {
    private val cameraYRotate = Rotate(0.0, Rotate.Y_AXIS)
    private var meshView: MeshView? = null
    private var tilesModel: Node
    private var material: Material? = null
    private val root = getRoot() as Group

    init {
        val camera = PerspectiveCamera(true)
        val cameraLookXRotate = Rotate(-30.0, Rotate.X_AXIS)
        val cameraLookZRotate = Rotate(0.0, Rotate.Z_AXIS)
        val cameraPosition = Translate(0.0, -122.0, -210.0)
        // Fake parallel camera, muahahaha
        camera.fieldOfView = 1.0
        camera.transforms.addAll(cameraYRotate, cameraPosition, cameraLookXRotate, cameraLookZRotate)
        camera.nearClip = 5.0
        camera.farClip = 500.0
        setCamera(camera)
        root.children.add(camera)
        observableMeshData.addListener { _, _, newValue -> newValue?.let { loadModel(it) } }
        observableTexture.addListener { _, _, newValue -> newValue?.let { setTexture(it) } }
        observableTiles.addListener(InvalidationListener { loadTiles(observableTiles) })
        tilesModel = Group()
        observableMeshData.value?.let { loadModel(it) }
        observableTexture.value?.let { setTexture(it) }
        loadTiles(observableTiles)
        op()
    }

    fun rotateY(amount: Number) {
        cameraYRotate.angle -= amount.toDouble()
    }

    private fun loadModel(meshData: MeshData): Boolean {
        root.children.remove(meshView)
        val meshView = ObjImporter().importModel(meshData)
        if (meshView != null) {
            meshView.transforms.add(Rotate(180.0, Rotate.Z_AXIS))
            root.children.addAll(meshView)
            this.meshView = meshView
            updateMaterial()
            return true
        }
        return false
    }

    private fun loadTiles(tiles: List<Vector2>) {
        val meshData = MeshData(javaClass.getResourceAsStream("/models/tilemarker.obj"))
        val texture = Image(javaClass.getResourceAsStream("/models/tilemarker.png"))
        val mesh = ObjImporter().importMesh(meshData)
        val material = PhongMaterial()
        material.diffuseMap = texture

        val tilesWidth = (tiles.maxBy { it.x }?.x ?: 0) + 1
        val tilesHeight = (tiles.maxBy { it.y }?.y ?: 0) + 1
        val startX = -tilesWidth / 2.0 + 0.5
        val startY = -tilesHeight / 2.0 + 0.5

        root.children.remove(tilesModel)
        tilesModel = Group()

        tiles.forEach {
            val meshView = MeshView(mesh)
            meshView.transforms.add(Rotate(180.0, Rotate.Z_AXIS))
            meshView.transforms.add(Translate(-startX - it.x, 0.0, startY + it.y))
            meshView.material = material
            tilesModel.add(meshView)
        }

        root.children.add(tilesModel)
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
