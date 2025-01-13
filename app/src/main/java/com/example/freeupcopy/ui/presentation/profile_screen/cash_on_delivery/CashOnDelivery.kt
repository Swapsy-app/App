import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.freeupcopy.R
import com.example.freeupcopy.ui.presentation.profile_screen.ExtraScreenViewModel
import com.example.freeupcopy.ui.theme.LightBlue
import com.example.freeupcopy.ui.theme.SwapGoBlue

@Composable
fun CashOnDeliveryScreen(
    viewModel: ExtraScreenViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item { HeaderSection() }
        item { CODToggleSection(switchCond = state.cashOnDelivery, toggle = { viewModel.toggleCod() }) }
        item { CODStepsSection() }
        item { ParcelReturnBenefitsSection() }
    }
}

@Composable
private fun HeaderSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SwapGoBlue)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.cash_on_delivery_),
            contentDescription = "cash on delivery",
            modifier = Modifier.size(80.dp)
        )
    }
}

@Composable
fun CODToggleSection(
    switchCond : Boolean,
    toggle : () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Cash On Delivery", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Allow buyers to pay at delivery for orders below 5000 Coins or ₹2000", modifier = Modifier.fillMaxWidth(0.8f))
            Switch(
                checked = switchCond, onCheckedChange = {toggle()},
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
        if(switchCond){
            Spacer(Modifier.height(8.dp))
            Card(
                colors = CardColors(
                    containerColor = Color(0xFFF6E5E4),
                    contentColor = Color.Black,
                    disabledContentColor = Color.Black,
                    disabledContainerColor = Color(0xFFF6E5E4)
                ),
            ) {
                Text("⚠\uFE0F On average, 1 out of 5 COD orders is likely to be returned.",modifier = Modifier.padding(16.dp), color = Color.Red)
            }
        }
    }
}

@Composable
fun CODStepsSection() {
    val steps = listOf(
        Pair("Buyers opting for COD will be charged an additional ₹50 per order at the checkout page, which goes to our delivery partners as COD handling fees.", ""),
        Pair("Sellers do not need to pay any additional cost or commission if they allow buyers to use the COD option.", ""),
        Pair("Sellers will pack and ship the product as usual.", "")
    )
    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        colors = CardColors(
            containerColor = Color(0xFFDDEFF7),
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color(0xFFEDF2F7)
        )
    ){
        Column(modifier = Modifier.padding(16.dp)){
            Text(
                text = "How Cash on Delivery Works",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            steps.forEach { (text, icon) ->
                CODStepItem(text, icon)
            }
        }
    }
}

@Composable
fun CODStepItem(text: String, icon: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("🔸", fontSize = 24.sp)
        Spacer(Modifier.width(4.dp))
        Text(text, fontSize = 16.sp)
    }
}

@Composable
fun ParcelReturnBenefitsSection() {
    ElevatedCard(
        modifier = Modifier.padding(16.dp),
        colors = CardColors(
            containerColor = Color(0xFFDDEFF7),
            contentColor = Color.Black,
            disabledContentColor = Color.Black,
            disabledContainerColor = Color(0xFFEDF2F7)
        )
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text("What you get if parcel returns", fontSize = 18.sp, fontWeight = FontWeight.Bold)

            val benefits = listOf(
                Pair("🔄 Automatic Re-Post", "Returned items will be automatically listed for resale on SwapGo, simplifying the process for you."),
                Pair("🚀 No Penalty", "If your product is returned, you can resell it on SwapGo without any penalty."),
            )

            benefits.forEach { (title, desc) ->
                BenefitItem(title, desc)
            }
        }
    }
}

@Composable
fun BenefitItem(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(end = 8.dp))
        Row{
            Spacer(Modifier.width(32.dp))
            Text(description, fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCODScreen() {
    CashOnDeliveryScreen()
}
