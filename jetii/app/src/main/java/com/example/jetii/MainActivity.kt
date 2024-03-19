package com.example.jetii

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetii.ui.theme.JetiiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Hello!",
                fontSize = 24.sp,
                modifier = modifier.align(alignment = Alignment.CenterHorizontally).align(Alignment.CenterHorizontally)
            )
            LazyA()
            RowA()
        }

    }


}

@Composable
fun LazyA(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(16.dp) // Adjust the radius as needed
                )
                .width(80.dp)
                .border(
                    width = 2.dp,
                    color = Color.Cyan,
                    shape = RoundedCornerShape(16.dp)
                )
                .height(80.dp)
        ) {
        }

        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(color = Color.Red)
                .width(50.dp)
                .height(50.dp)
        ) {

            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(color = Color.Cyan)
                    .width(30.dp)
                    .height(30.dp)
            ) {

            }

        }
    }
}

@Composable
fun RowA(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    val context = LocalContext.current

    TextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        modifier = Modifier
            .padding()
            .fillMaxWidth(),
        label = { Text(text = "Enter your Name") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Email Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show()
            }) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Send Icon"
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send,keyboardType = KeyboardType.Email),
        singleLine = true
    )

    OutlinedTextField(
        value = text2,
        onValueChange = { newText ->
            text2 = newText
        },
        modifier = Modifier
            .padding(top=10.dp)
            .fillMaxWidth(),
        label = { Text(text = "Enter your Name") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Email Icon"
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                Toast.makeText(context, text2, Toast.LENGTH_LONG).show()

            }) {
                Icon(
                    imageVector = Icons.Outlined.Send,
                    contentDescription = "Send Icon"
                )
            }
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send,keyboardType = KeyboardType.Email),
        singleLine = true
    )

    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
            .padding(top = 10.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(30.dp))
    ) {
        Icon(imageVector = Icons.Filled.Face, contentDescription = null, tint = Color.Black)
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
        Text(text = "Amin")
    }

    Button(
        onClick = { /*TODO*/ },
        shape = CutCornerShape(10.dp),

    ) {
        Icon(imageVector = Icons.Filled.Face, contentDescription = null, tint = Color.White)
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSize))
        Text(
            text = "Second Button",
            fontFamily = FontFamily(Font(R.font.montserrat_medium))
        )
    }
}


@Preview(showBackground = true, name = "Amin")
@Composable
fun GreetingPreview() {
    JetiiTheme {
        Greeting()
    }
}