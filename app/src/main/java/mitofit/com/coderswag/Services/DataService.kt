package mitofit.com.coderswag.Services

import mitofit.com.coderswag.Model.Category
import mitofit.com.coderswag.Model.Product

object DataService {
    val categories = listOf(
        Category("SHIRTS", "shirtimage"),
        Category("HOODIES","hoodieimage"),
        Category("Hats","hatimage"),
        Category("DIGITAL","digitalgoodsimage")
    )
    val hats = arrayListOf(
        Product("Developes Graphic beanie","hat01","$18"),
        Product("Developes Hat Black","hat02","$20"),
        Product("Developes Hat White","hat03","$18"),
        Product("Developes Hat Snapback","hat04","$22")
    )

    val hoodies = arrayListOf(
        Product("Devslopes Hoodie Gray","hoodie01","$28"),
        Product("Devslopes Hoodie Red","hoodie02","$32"),
        Product("Devslopes Hoodie white","hoodie03","$28"),
        Product("Devslopes Hoodie Black","hoodie04","$42")
    )
    val shirts = arrayListOf(
        Product("Devslopes Shirt Gray","shirt01","$20"),
        Product("Devslopes Shirt Red","shirt02","$22"),
        Product("Devslopes Shirt white","shirt03","$23"),
        Product("Devslopes Shirt Black","shirt04","$25"),
        Product("Kickflip Studio","shirt05","$18")
    )
}