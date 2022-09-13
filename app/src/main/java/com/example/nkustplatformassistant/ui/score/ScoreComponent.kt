package com.example.nkustplatformassistant.ui.score

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nkustplatformassistant.data.persistence.db.entity.ScoreEntity
import com.example.nkustplatformassistant.ui.theme.Nkust_platform_assistantTheme

@Composable
fun ScoreContent(scoreViewModel: ScoreViewModel) {
    val scores by scoreViewModel.scores.observeAsState()

    Column(
        modifier = Modifier.padding(
            PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            )
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
//            contentPadding = PaddingValues(
//                start = 12.dp,
//                top = 16.dp,
//                end = 12.dp,
//                bottom = 16.dp
//            ),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
//                .border(1.dp, Color.Gray, RoundedCornerShape(4))

        ) {
            if (scores != null || scores!!.isNotEmpty()) {
                item(1) { Text("Subject", fontWeight = FontWeight.Bold) }
                item(2) { Text("Mid score", fontWeight = FontWeight.Bold) }
                item(3) { Text("Final score", fontWeight = FontWeight.Bold) }
            }

            scores?.forEachIndexed { index, scores ->
                item(3 * (index + 1) + 1) { scores?.subjectName?.let { Text(it) } }
                item(3 * (index + 1) + 2) { scores?.midScore?.let { Text(it) } }
                item(3 * (index + 1) + 3) { scores?.finalScore?.let { Text(it) } }
            }
        }

        Divider(
            modifier = Modifier.padding(
                PaddingValues(
                    top = 16.dp,
                    bottom = 16.dp
                )
            )
        )

        LazyColumn {
            item {
                Row {
                    Text("123")
                    Spacer(Modifier.weight(1f))
                    Text("123")
                    Spacer(Modifier.weight(1f))
                    Text("123")
                    Spacer(Modifier.weight(1f))
                }
            }
            item {
                Row {
                    Text("123456")
                    Spacer(Modifier.weight(1f))
                    Text("123")
                    Spacer(Modifier.weight(1f))
                    Text("123")
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    Nkust_platform_assistantTheme {
        val scoreViewModel = ScoreViewModel()
        scoreViewModel.rS(
            mutableListOf(
                ScoreEntity(
                    1,
                    2,
                    "3",
                    "123",
                    "s1",
                    "m1",
                ),
                ScoreEntity(
                    1,
                    2,
                    "3",
                    "123",
                    "s1",
                    "m1",
                ),
                ScoreEntity(
                    1,
                    2,
                    "3",
                    "123",
                    "s1",
                    "m1",
                ),
            )
        )
        ScoreContent(scoreViewModel)
    }
}
