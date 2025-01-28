package pl.dawidfendler.dailyeaseassistant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import pl.dawidfendler.R
import pl.dawidfendler.ui.theme.dp_1
import pl.dawidfendler.ui.theme.dp_12
import pl.dawidfendler.ui.theme.dp_16
import pl.dawidfendler.ui.theme.dp_20
import pl.dawidfendler.ui.theme.dp_36
import pl.dawidfendler.ui.theme.dp_48
import pl.dawidfendler.ui.theme.dp_6
import pl.dawidfendler.ui.theme.dp_8
import pl.dawidfendler.ui.theme.sp_14
import pl.dawidfendler.ui.theme.sp_20

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    modifier: Modifier = Modifier,
    name: String = "User",
    image: String = "",
    onUserIconClick: () -> Unit = {},
    onMenuItemClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier
            .padding(top = dp_12),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        title = {
            Row(
                modifier = Modifier
                    .padding(end = dp_16)
                    .height(dp_48),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(dp_36)
                        .border(
                            width = dp_1,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(dp_12)
                        )
                        .padding(dp_6)
                        .clickable(
                            onClick = {
                                onUserIconClick.invoke()
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = dp_20
                            )
                        )
                ) {
                    Image(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.LightGray),
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.width(dp_16))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        fontSize = sp_20,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                Font(pl.dawidfendler.ui.R.font.my_font)
                            ),
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = stringResource(R.string.home_welcome_text),
                        fontSize = sp_14,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                Font(pl.dawidfendler.ui.R.font.my_font)
                            )
                        ),
                        color = Color.LightGray
                    )
                }

                Box(
                    modifier = Modifier
                        .size(dp_36)
                        .border(
                            width = dp_1,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(dp_12)
                        )
                        .padding(dp_8)
                ) {
                    Icon(
                        imageVector = Icons.Default.GridView,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                onClick = {
                                    onMenuItemClick.invoke()
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = false,
                                    radius = dp_20
                                )
                            )
                    )
                }
            }
        }
    )
}