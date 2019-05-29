package org.theponies.ponecrafter.util.importer

import java.io.*
import java.util.HashMap
import java.util.logging.Level
import java.util.logging.Logger

import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.shape.CullFace
import javafx.scene.shape.MeshView
import javafx.scene.shape.TriangleMesh
import org.theponies.ponecrafter.model.MeshData

class ObjImporter {
    private val vertexes = arrayListOf<Float>()
    private val uvs = arrayListOf<Float>()
    private val faces = arrayListOf<Int>()
    private val smoothingGroups = arrayListOf<Int>()
    private val normals = arrayListOf<Float>()
    private val faceNormals = arrayListOf<Int>()
    private var facesStart = 0
    private var facesNormalStart = 0
    private var smoothingGroupsStart = 0

    private val meshes = HashMap<String, TriangleMesh>()

    fun importModel(meshData: MeshData): Node {
        val group = Group()
        read(meshData)
        for (mesh in getMeshes()) {
            group.children.add(buildMeshView(mesh))
        }
        return group
    }

    private fun vertexIndex(vertexIndex: Int): Int {
        return if (vertexIndex < 0) {
            vertexIndex + vertexes.size / 3
        } else {
            vertexIndex - 1
        }
    }

    private fun uvIndex(uvIndex: Int): Int {
        return if (uvIndex < 0) {
            uvIndex + uvs.size / 2
        } else {
            uvIndex - 1
        }
    }

    private fun normalIndex(normalIndex: Int): Int {
        return if (normalIndex < 0) {
            normalIndex + normals.size / 3
        } else {
            normalIndex - 1
        }
    }

    private fun getMeshes(): Set<String> {
        return meshes.keys
    }

    private fun buildMeshView(key: String): MeshView {
        val meshView = MeshView()
        meshView.id = key
        meshView.mesh = meshes[key]
        meshView.cullFace = CullFace.NONE
        return meshView
    }

    @Throws(IOException::class)
    private fun read(meshData: MeshData) {
        val br = BufferedReader(InputStreamReader(ByteArrayInputStream(meshData.data)))
        var currentSmoothGroup = 0
        var key = "default"
        br.forEachLine { line ->
            try {
                if (line.startsWith("g ") || line == "g") {
                    addMesh(key)
                    key = if (line.length > 2) line.substring(2) else "default"
                } else if (line.startsWith("v ")) {
                    val split = line.substring(2).trim { it <= ' ' }.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val x = java.lang.Float.parseFloat(split[0])
                    val y = java.lang.Float.parseFloat(split[1])
                    val z = java.lang.Float.parseFloat(split[2])

                    vertexes.add(x)
                    vertexes.add(y)
                    vertexes.add(z)

                } else if (line.startsWith("vt ")) {
                    val split = line.substring(3).trim { it <= ' ' }.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val u = java.lang.Float.parseFloat(split[0])
                    val v = java.lang.Float.parseFloat(split[1])

                    uvs.add(u)
                    uvs.add(1 - v)
                } else if (line.startsWith("f ")) {
                    val split = line.substring(2).trim { it <= ' ' }.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val data = arrayOfNulls<IntArray>(split.size)
                    var uvProvided = true
                    var normalProvided = true
                    for (i in split.indices) {
                        val split2 = split[i].split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        if (split2.size < 2) {
                            uvProvided = false
                        }
                        if (split2.size < 3) {
                            normalProvided = false
                        }
                        val dataI = IntArray(split2.size)
                        data[i] = dataI
                        for (j in split2.indices) {
                            if (split2[j].isEmpty()) {
                                dataI[j] = 0
                                if (j == 1) {
                                    uvProvided = false
                                }
                                if (j == 2) {
                                    normalProvided = false
                                }
                            } else {
                                dataI[j] = Integer.parseInt(split2[j])
                            }
                        }
                    }
                    val v1 = vertexIndex(data[0]!![0])
                    var uv1 = -1
                    var n1 = -1
                    if (uvProvided) {
                        uv1 = uvIndex(data[0]!![1])
                        if (uv1 < 0) {
                            uvProvided = false
                        }
                    }
                    if (normalProvided) {
                        n1 = normalIndex(data[0]!![2])
                        if (n1 < 0) {
                            normalProvided = false
                        }
                    }
                    for (i in 1 until data.size - 1) {
                        val v2 = vertexIndex(data[i]!![0])
                        val v3 = vertexIndex(data[i + 1]!![0])
                        var uv2 = -1
                        var uv3 = -1
                        var n2 = -1
                        var n3 = -1
                        if (uvProvided) {
                            uv2 = uvIndex(data[i]!![1])
                            uv3 = uvIndex(data[i + 1]!![1])
                        }
                        if (normalProvided) {
                            n2 = normalIndex(data[i]!![2])
                            n3 = normalIndex(data[i + 1]!![2])
                        }

                        faces.add(v1)
                        faces.add(uv1)
                        faces.add(v2)
                        faces.add(uv2)
                        faces.add(v3)
                        faces.add(uv3)
                        faceNormals.add(n1)
                        faceNormals.add(n2)
                        faceNormals.add(n3)

                        smoothingGroups.add(currentSmoothGroup)
                    }
                } else if (line.startsWith("s ")) {
                    currentSmoothGroup = if (line.substring(2) == "off") {
                        0
                    } else {
                        Integer.parseInt(line.substring(2))
                    }
                } else if (line.startsWith("vn ")) {
                    val split = line.substring(2).trim { it <= ' ' }.split(" +".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val x = java.lang.Float.parseFloat(split[0])
                    val y = java.lang.Float.parseFloat(split[1])
                    val z = java.lang.Float.parseFloat(split[2])
                    normals.add(x)
                    normals.add(y)
                    normals.add(z)
                }
            } catch (ex: Exception) {
                Logger.getLogger(ObjImporter::class.java.name).log(Level.SEVERE, "Failed to parse line:$line", ex)
            }

        }
        addMesh(key)
    }

    private fun addMesh(key: String) {
        if (facesStart >= faces.size) {
            // we're only interested in faces
            smoothingGroupsStart = smoothingGroups.size
            return
        }
        val vertexMap = HashMap<Int, Int>(vertexes.size / 2)
        val uvMap = HashMap<Int, Int>(uvs.size / 2)
        val normalMap = HashMap<Int, Int>(normals.size / 2)
        val newVertexes = ArrayList<Float>(vertexes.size / 2)
        val newUVs = ArrayList<Float>(uvs.size / 2)
        val newNormals = ArrayList<Float>(normals.size / 2)
        var useNormals = true

        var i = facesStart
        while (i < faces.size) {
            val vi = faces[i]
            var nvi: Int? = vertexMap[vi]
            if (nvi == null) {
                nvi = newVertexes.size / 3
                vertexMap[vi] = nvi
                newVertexes.add(vertexes[vi * 3])
                newVertexes.add(vertexes[vi * 3 + 1])
                newVertexes.add(vertexes[vi * 3 + 2])
            }
            faces[i] = nvi

            val uvi = faces[i + 1]
            var nuvi: Int? = uvMap[uvi]
            if (nuvi == null) {
                nuvi = newUVs.size / 2
                uvMap[uvi] = nuvi
                if (uvi >= 0) {
                    newUVs.add(uvs[uvi * 2])
                    newUVs.add(uvs[uvi * 2 + 1])
                } else {
                    newUVs.add(0f)
                    newUVs.add(0f)
                }
            }
            faces[i + 1] = nuvi

            if (useNormals) {
                val ni = faceNormals[i / 2]
                var nni: Int? = normalMap[ni]
                if (nni == null) {
                    nni = newNormals.size / 3
                    normalMap[ni] = nni
                    if (ni >= 0 && normals.size >= (ni + 1) * 3) {
                        newNormals.add(normals[ni * 3])
                        newNormals.add(normals[ni * 3 + 1])
                        newNormals.add(normals[ni * 3 + 2])
                    } else {
                        useNormals = false
                        newNormals.add(0f)
                        newNormals.add(0f)
                        newNormals.add(0f)
                    }
                }
                faceNormals[i / 2] = nni
            }
            i += 2
        }

        val mesh = TriangleMesh()
        mesh.points.setAll(*newVertexes.toFloatArray())
        mesh.texCoords.setAll(*newUVs.toFloatArray())
        mesh.faces.setAll(*faces.subList(facesStart, faces.size).toIntArray())

        if (useNormals) {
            val newFaces = faces.subList(facesStart, faces.size).toIntArray()
            val newFaceNormals = faceNormals.subList(facesNormalStart, faceNormals.size).toIntArray()
            val smGroups = SmoothingGroups.calcSmoothGroups(mesh, newFaces, newFaceNormals, newNormals.toFloatArray())
            mesh.faceSmoothingGroups.setAll(*smGroups)
        } else {
            mesh.faceSmoothingGroups.setAll(*smoothingGroups.subList(smoothingGroupsStart, smoothingGroups.size).toIntArray())
        }

        var keyIndex = 2
        var theKey = key
        while (meshes[theKey] != null) {
            theKey = key + " (" + keyIndex++ + ")"
        }
        meshes[theKey] = mesh

        facesStart = faces.size
        facesNormalStart = faceNormals.size
        smoothingGroupsStart = smoothingGroups.size
    }
}
