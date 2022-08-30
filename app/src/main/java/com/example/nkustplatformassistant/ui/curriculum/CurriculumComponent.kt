package com.example.nkustplatformassistant.ui.curriculum

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.nkustplatformassistant.ui.theme.Nkust_platform_assistantTheme
import com.soywiz.klogger.AnsiEscape

@Composable
fun CurriculumContext() {
    val itemsList = (0..80).toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        )
    ) {
        items(77) { index ->
            if (index == 0)
                Text("")
            else if (index < 7)
                Text(Week[index - 1].name)
            else if (index % 7 == 0) {
                val curriculumTime = CurriculumTime[(index / 7) - 1]
                CurriculumTimeCard(curriculumTime)
            } else
                Text(itemsList[index].toString())


        }
//        courseItem(
//            Course(
//                "123",
//                "123",
//                "123",
//                "123",
//                "123",
//                "123",
//                false,
//                "123",
//                "123",
//                "123",
//                "123"
//            )
//        )

    }
}

@Composable
fun DisplayOption() {
    val state = rememberScrollState()
    LaunchedEffect(Unit) { state.animateScrollTo(100) }

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .horizontalScroll(state)
    ) {
        Icon(
            imageVector = Icons.Default.AutoAwesome,
            contentDescription = null
        )
        Text(text = "Display:")

//        ChipCell()

//        repeat(10) {
//            Text("Item $it", modifier = Modifier.padding(2.dp))
//        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChipCell(lable: String, state: State<Boolean>, onClick: () -> Unit) {
//    val uid: String by loginParamsViewModel.uid.observeAsState("")
//    val pwd: String by loginParamsViewModel.pwd.observeAsState("")
    FilterChip(
        selected = state.value,
        onClick = onClick,
        label = { lable }
    )
}


fun LazyGridScope.CourseItem(course: Course) {
    val index: Int = 12

    item(
        key = { index },
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        CourseCard(course)
    }
}

@Composable
fun CourseCard(course: Course) {
    Card(
        modifier = Modifier.padding(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(course.courseName)
            Text(course.professor)
            Text(course.classLocation)
        }
    }
}

@Composable
fun CurriculumTimeCard(curriculumTime: CurriculumTime) {
    Card(
        modifier = Modifier.padding(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                curriculumTime.id,
                modifier = Modifier
                    .clip(shape)
                    .background(Color.LightGray)
                    .padding(2.dp)
            )
            Text(curriculumTime.startTime)
            Text(curriculumTime.endTime)
        }
    }
}

//{ //top
//    item(span = { GridItemSpan(6) }) { TopInfo() } //一行2個
//    itemsIndexed(items = list1, spans = { _, _ -> GridItemSpan(3) },
//        itemContent = { index, item -> GridItemContent(item.second) })
//    //一行3個
//    itemsIndexed(items = list2, spans = { _, _ -> GridItemSpan(2) },
//        itemContent = { index, item -> GridItemContent(item.second) })
//}

// ------------------

@Preview(showBackground = true)
@Composable
fun CurriculumTimeCardPreview() {
    CurriculumTimeCard(CurriculumTime[1])
//    CurriculumTimeCard(CurriculumTime[2])

}

@Preview(showBackground = true)
@Composable
fun CourseBlockPreview() {
    Nkust_platform_assistantTheme {
        CourseCard(
            Course(
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                false,
                "123",
                "123",
                "123",
                "123"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Nkust_platform_assistantTheme {
        CurriculumContext()
    }
}

