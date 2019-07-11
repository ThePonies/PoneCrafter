package org.theponies.ponecrafter.model

import tornadofx.ViewModel

class FurnitureModel(val furniture: Furniture = Furniture()) : ViewModel() {
    val item = furniture

    val name = bind { furniture.nameProperty }
    val description = bind { furniture.descriptionProperty }
    val price = bind { furniture.priceProperty }
    val uuid = bind { furniture.uuidProperty }
    
    val category = bind { furniture.categoryProperty }
    val placementType = bind { furniture.placementTypeProperty }
    val pickupable = bind { furniture.pickupableProperty }
    val sellable = bind { furniture.sellableProperty }
    val occupiedTiles = furniture.occupiedTilesProperty.value
    val requiredAge = bind { furniture.requiredAgeProperty }

    val hungerStat = bind { furniture.needStats.hungerProperty }
    val energyStat = bind { furniture.needStats.energyProperty }
    val comfortStat = bind { furniture.needStats.comfortProperty }
    val funStat = bind { furniture.needStats.entertainmentProperty }
    val hygieneStat = bind { furniture.needStats.hygieneProperty }
    val socialStat = bind { furniture.needStats.socialProperty }
    val bladderStat = bind { furniture.needStats.bladderProperty }
    val roomStat = bind { furniture.needStats.roomProperty }

    val cookingStat = bind { furniture.skillStats.cookingProperty }
    val mechanicalStat = bind { furniture.skillStats.mechanicalProperty }
    val charismaStat = bind { furniture.skillStats.charismaProperty }
    val bodyStat = bind { furniture.skillStats.bodyProperty }
    val logicStat = bind { furniture.skillStats.logicProperty }
    val creativityStat = bind { furniture.skillStats.creativityProperty }

    val meshData = bind { furniture.meshDataProperty }
    val texture = bind { furniture.textureProperty }
}