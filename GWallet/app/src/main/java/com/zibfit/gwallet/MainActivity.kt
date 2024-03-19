package com.zibfit.gwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.wallet.OfferWalletObject
import com.google.android.gms.wallet.wobs.TextModuleData
import com.google.android.gms.wallet.wobs.WalletObjectsConstants
import com.sun.org.apache.xml.internal.serializer.utils.Utils.messages
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt.setOnClickListener {
        val wob = OfferWalletObject.newBuilder()
            .setClassId("2967745143867465930" + "." + "01_LoyaltyClass")
            .setId("2967745143867465930" + "." + "01_LoyaltyObjectId_11")
            .setState(WalletObjectsConstants.State.ACTIVE)
            .setBarcodeType("qr")

            .setIssuerName("Baconrista")

            .build()

        }
        // Define the Image Module Data
        // Define the Image Module Data
        val imageModuleData: MutableList<ImageModuleData> = ArrayList<ImageModuleData>()

        val image: ImageModuleData = ImageModuleData().setMainImage(
            Image().setSourceUri(
                ImageUri().setUri("http://farm4.staticflickr.com/3738/12440799783_3dc3c20606_b.jpg")
            )
        )

        imageModuleData.add(image)

// Define Text Module Data

// Define Text Module Data
        val textModulesData: MutableList<TextModuleData> = ArrayList()

        val textModuleData: TextModuleData = TextModuleData().setHeader("Rewards details")
            .setBody(
                "Welcome to Baconrista rewards.  Enjoy your rewards for being a loyal customer.  10 points for ever dollar spent.  Redeem your points for free coffee, bacon and more!"
            )
        textModulesData.add(textModuleData)

// Define Links Module Data

// Define Links Module Data
        val uris: MutableList<Uri> = ArrayList<Uri>()
        val uri1: Uri =
            Uri().setDescription("Nearby Locations").setUri("http://maps.google.com/?q=google")
        val uri2: Uri = Uri().setDescription("Call Customer Service").setUri("tel:6505555555")

        uris.add(uri1)
        uris.add(uri2)

        val linksModuleData: LinksModuleData = LinksModuleData().setUris(uris)


// Define general messages


// Define general messages
        val messages: MutableList<Message> = ArrayList<Message>()
        val message: Message = Message()
            .setHeader("Welcome to Baconrista")
            .setBody("Featuring our new bacon donuts.")
        messages.add(message)

// Define Geofence locations

// Define Geofence locations
        val locations: MutableList<LatLongPoint> = ArrayList<LatLongPoint>()
        locations.add(
            LatLongPoint().setLatitude(37.422601).setLongitude(
                -122.085286
            )
        )
        locations.add(
            LatLongPoint().setLatitude(37.424354).setLongitude(
                -122.09508869999999
            )
        )
        locations.add(
            LatLongPoint().setLatitude(40.7406578).setLongitude(
                -74.00208940000002
            )
        )

// Create class

// Create class
        val wobClass: LoyaltyClass = LoyaltyClass()
            .setId('2945482443380251551.ExampleClass1')
            .setIssuerName("Baconrista")
            .setProgramName("Baconrista Rewards")
            .setProgramLogo(
                Image().setSourceUri(
                    ImageUri()
                        .setUri("http://farm8.staticflickr.com/7340/11177041185_a61a7f2139_o.jpg")
                )
            )
            .setRewardsTierLabel("Tier").setRewardsTier("Gold")
            .setImageModulesData(imageModuleData)
            .setTextModulesData(textModulesData)
            .setLinksModuleData(linksModuleData)
            .setAccountNameLabel("Member Name").setAccountIdLabel("Member Id")
            .setMessages(messages)
            .setReviewStatus("underReview")
            .setMultipleDevicesAndHoldersAllowedStatus("multipleHolders")
            .setLocations(locations)

        val response: LoyaltyClass = client.loyaltyclass().insert(wobClass).execute()


    }
}