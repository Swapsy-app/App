import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.profile_screen.ExtraScreenViewModel
import com.example.freeupcopy.ui.presentation.profile_screen.holiday.DatePickerDocked
import com.example.freeupcopy.ui.presentation.setting.componants.TaxInfo
import com.example.freeupcopy.ui.theme.SwapGoBlue
import com.example.freeupcopy.ui.theme.SwapGoYellow

@Composable
fun HolidayScreen(
    viewModel: ExtraScreenViewModel = viewModel()
){
    val state by viewModel.state.collectAsState()

    Box {
        Column(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SwapGoBlue)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.holidays),
                    contentDescription = "cash on delivery",
                    modifier = Modifier.size(80.dp)
                )
            }
            Column(modifier = Modifier.padding(16.dp)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Holiday Mode", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Switch(
                        checked = state.holidayMode, onCheckedChange = {viewModel.toggleHoliday()},
                        colors = SwitchColors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SwapGoBlue,
                            checkedBorderColor = SwapGoBlue,
                            checkedIconColor = SwapGoBlue,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.LightGray,
                            uncheckedBorderColor = Color.White,
                            uncheckedIconColor = Color.LightGray,
                            disabledCheckedThumbColor = Color.White.copy(alpha = 0.6f), // Adjust opacity for disabled state
                            disabledCheckedTrackColor = SwapGoBlue.copy(alpha = 0.6f),
                            disabledCheckedBorderColor = SwapGoBlue.copy(alpha = 0.6f),
                            disabledCheckedIconColor = SwapGoBlue.copy(alpha = 0.6f),
                            disabledUncheckedThumbColor = Color.LightGray.copy(alpha = 0.6f),
                            disabledUncheckedTrackColor = Color.LightGray.copy(alpha = 0.6f),
                            disabledUncheckedBorderColor = Color.LightGray.copy(alpha = 0.6f),
                            disabledUncheckedIconColor = Color.LightGray.copy(alpha = 0.6f)
                        )
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text("When holiday mode is ON, buyers won't be able to buy or make offers on your items. However, they can still view, like, comment and share your items. ", modifier = Modifier.fillMaxWidth())
                if(state.holidayMode){
                    Column(Modifier.animateContentSize()) {
                        Spacer(Modifier.height(16.dp))
                        DatePickerDocked(
                            label = "Start Date"
                        )
                        Spacer(Modifier.height(16.dp))
                        DatePickerDocked(
                            label = "End Date"
                        )
                        Spacer(Modifier.height(32.dp))
                        Card(
                            colors = CardColors(
                                containerColor = Color(0xFFF6E5E4),
                                contentColor = Color.Black,
                                disabledContentColor = Color.Black,
                                disabledContainerColor = Color(0xFFF6E5E4)
                            ),
                        ) {
                            Text("⚠\uFE0F If End Date is not selected, Holiday mode has to manually turned on.",modifier = Modifier.padding(16.dp), color = Color.Red, fontSize = 14.sp)
                        }
                    }
                }

            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 16.dp)
                .align(alignment = Alignment.BottomCenter)
                .clickable {  }
                .background(color = SwapGoBlue),

            ) {
            Text("Save", fontSize = 24.sp, color = SwapGoYellow, fontWeight = FontWeight.Bold, modifier = Modifier.align(
                Alignment.Center)
            )
        }
    }
}


@Preview(showBackground = true) @Composable
fun PreviewSetting(){
    HolidayScreen()
}