import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.freeupcopy.ui.presentation.home_screen.componants.BottomNavigationItem
import com.example.freeupcopy.ui.presentation.product_listing.componants.Chip


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun InboxScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Messages") },
                actions = {
                    TextButton(onClick = { /* Handle Read All action */ }) {
                        Text(text = "Read All", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        },
    ) { innerPadding ->
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Categories
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                val categories = listOf(
                    "Offers", "Comments", "Item Review", "Order Updates",
                    "Earnings", "Community 4", "SwapGo Updates", "Item Updates"
                )
                categories.forEach { category ->
                    Column(
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Row {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .clickable {

                                    }
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = category,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Message list
            LazyColumn {
                items(messages) { message ->
                    MessageItem(message)
                    Divider()
                }
            }
        }
    }
}


@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // Message details
        Column {
            Text(
                text = message.title,
            )
            Text(
                text = message.timestamp,
                color = Color.Gray
            )
        }
    }
}

// Sample data
data class Message(val title: String, val timestamp: String)

val messages = listOf(
    Message("@sakshi_rathore6170 is following you!", "01/12 16:04"),
    Message("@suman6701 is following you!", "01/12 15:55"),
    Message("@retrobazaar is following you!", "01/11 22:54"),
    Message("@rekha_patel3942 is following you!", "01/11 22:20"),
    Message("Your coupon expires tomorrow ⏰", "01/10 14:01"),
    Message("An item you Liked is taken!", "01/09 18:30")
)

@Preview(showBackground = true) @Composable
fun PreviewInboxScreen(){
    InboxScreen()
}