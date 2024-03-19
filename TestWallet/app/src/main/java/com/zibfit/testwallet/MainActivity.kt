package com.zibfit.testwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.wallet.LoyaltyWalletObject
import com.google.android.gms.wallet.wobs.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Define Points
        // Define Points
        val points = LoyaltyPoints.newBuilder()
            .setLabel("Points")
            .setType("points")
            .setBalance(LoyaltyPointsBalance.newBuilder().setString("500").build()).build()

// Define Text Module Data

// Define Text Module Data
        val textModulesData: MutableList<String> = ArrayList()
        val textModuleData = TextModuleData(
            "Jane's Baconrista Rewards",
            "Save more at your local Mountain View store Jane.  You get 1 bacon fat latte for every 5 coffees purchased.  Also just for you, 10% off all pastries in the Mountain View store."
        )
        textModulesData.add(textModuleData.toString())

// Define Links Module Data

// Define Links Module Data
        val uris: MutableList<String> = ArrayList()
        val uri1 =
            UriData("http://www.baconrista.com/myaccount?id=1234567890", "My Baconrista Account")
        uris.add(uri1.toString())

        val imageUris: MutableList<String> = ArrayList()
        val uri2 =
            UriData("http://examplesite/images/exampleimage2.jpg", "Image Description")
        imageUris.add(uri2.toString())

// Define Info Module

// Define Info Module
        val row0cols: MutableList<String> = ArrayList()
        val row0col0 = LabelValue("Next Reward in", "2 coffees")
        val row0col1 = LabelValue("Member Since", "01/15/2013")
        row0cols.add(row0col0.toString())
        row0cols.add(row0col1.toString())

        val row1cols: MutableList<String> = ArrayList()
        val row1col0 = LabelValue("Local Store", "Mountain View")
        row1cols.add(row1col0.toString())

        val rows: MutableList<LabelValue> = ArrayList()
        val row0 = LabelValueRow.newBuilder().addColumns(row0cols).build()
        val row1 = LabelValueRow.newBuilder().addColumns(row1cols).build()

        rows.add(row0)
        rows.add(row1)

// Define general messages

// Define general messages
        val messages: MutableList<*> = ArrayList()
        val message = WalletObjectMessage.newBuilder()
            .setHeader("Hi Jane!")
            .setBody(
                "Thanks for joining our program. Show this message to " +
                        "our barista for your first free coffee on us!"
            )
            .build()
        messages.add(message)

// Define Geolocations


// Define Geolocations
        val location = LatLng(37.422601, -122.085286)

        val locations: MutableList<*> = ArrayList()
        locations.add(location)

        val wob = LoyaltyWalletObject
            .newBuilder()
            .setClassId("2967745143867465930.LoyaltyClass")
            .setId("2967745143867465930.LoyaltyObject")
            .setState(WalletObjectsConstants.State.ACTIVE)
            .setAccountId("1234567890")
            .setAccountName("Jane Doe")
            .setIssuerName("Baconrista")
            .setProgramName("Baconrista Rewards")
            .setBarcodeType("qrCode")
            .setBarcodeValue("28343E3")
            .setBarcodeAlternateText("12345")
            .setLoyaltyPoints(points)
            .addTextModulesData(textModulesData)
            .addLinksModuleDataUris(uris)
            .addInfoModuleDataLabelValueRows(rows)
            .addImageModuleDataMainImageUris(imageUris)
            .addMessages(messages)
            .addLocations(locations)
            .build()



    }

}