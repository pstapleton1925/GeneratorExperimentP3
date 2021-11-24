package randos

object materialsGen {

    val materialTypes = List("Metal", "Tree", "Earth", "Gem", "Shrub", "Animal", "Small_Plant_Fungus")

    val metalSchema = List("Name", "Location", "Quantity")
    val treeSchema = List("Name", "Location", "Quantity", "FruitYN")
    val earthSchema = List("Name", "Location", "Quantity")
    val gemSchema = List("Name", "Location", "Quantity")
    val shrubSchema = List("Name", "Location", "Quantity", "FruitYN")
    val animalSchema = List("Name", "Location", "Quantity", "ExpireTime")
    val plantFungusSchema = List("Name", "Location", "Quantity", "FruitYN", "FungusYN")

  def main: Unit = {

  }

}
