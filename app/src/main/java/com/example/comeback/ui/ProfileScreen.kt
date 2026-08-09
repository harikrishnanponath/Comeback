package com.example.comeback.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.comeback.R
import kotlin.random.Random



@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val random = Random.nextInt(0,2)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardColors(
                containerColor = Color(0xFF393D47),
                contentColor = Color.White,
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.LightGray
            )
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Box(modifier = Modifier.size(100.dp)) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier.size(18.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    ){
                        Icon(
                            imageVector = Icons.Default.Circle,
                            tint = if (random == 0) Color.Green else Color.Red,
                            contentDescription = "Active Status",
                            )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Harikrishnan Ponath",
                    style = MaterialTheme.typography.headlineSmall,

                    )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Android developer",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Green
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hi I'm an android developer, who loves to create apps that makes life much easier!",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "256,987",
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = "Followers",
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "178",
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = "Following",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.Black
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Follow",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Follow")
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Message,
                        contentDescription = "icon",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Message")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        val imageSize = 100.dp
        Box(
            modifier = Modifier.size(imageSize)
        ){
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(imageSize)
            )
            Box(
                modifier = Modifier.size(18.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .border(
                        width = 2.dp,
                        color = Color.White,
                        shape = CircleShape
                    )
                    .align(Alignment.BottomEnd)

            )

        }

    }

}