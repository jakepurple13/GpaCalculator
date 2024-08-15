package com.programmersbox.gpacalculator

import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.round

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme())
            darkColorScheme(
                primary = Color(0xff90CAF9),
                secondary = Color(0xff90CAF9),
            )
        else
            lightColorScheme(
                primary = Color(0xff2196F3),
                secondary = Color(0xff90CAF9),
            )
    ) { GpaScreen() }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GpaScreen(
    viewModel: GpaViewModel = viewModel { GpaViewModel() },
) {
    val gpaTotals by remember {
        derivedStateOf { GpaCalculator.calculate(viewModel.gpaList) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GPA Calculator") },
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        animateValueAsState(
                            gpaTotals.gpa,
                            DoubleConverter,
                            label = "GPA"
                        ).value.roundTo(2).toString(),
                        fontSize = 72.sp,
                        textAlign = TextAlign.Center,
                    )
                    Text("Gpa")
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            animateIntAsState(
                                gpaTotals.creditTotal,
                                label = "Credit Total"
                            ).value.toString(),
                            fontSize = 36.sp,
                            textAlign = TextAlign.Center,
                        )
                        Text("Credit Total")
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            animateValueAsState(
                                gpaTotals.gradePointTotal,
                                DoubleConverter,
                                label = "Grade Point Total"
                            ).value.roundTo(2).toString(),
                            fontSize = 36.sp,
                            textAlign = TextAlign.Center,
                        )
                        Text("Grade Point Total")
                    }
                }
            }

            viewModel.gpaList.forEachIndexed { index, gradeForClass ->
                item {
                    OutlinedTextField(
                        gradeForClass.courseName,
                        onValueChange = { viewModel.modifyCourseName(index, it) },
                        label = { Text("Course") },
                        singleLine = true,
                        modifier = Modifier.animateItem()
                    )
                }

                item {
                    OutlinedTextField(
                        gradeForClass.credit.toString(),
                        onValueChange = { viewModel.modifyCredit(index, it.toIntOrNull() ?: 0) },
                        label = { Text("Credits") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.animateItem()
                    )
                }

                item {
                    var showDropDown by remember { mutableStateOf(false) }

                    ExposedDropdownMenuBox(
                        expanded = showDropDown,
                        onExpandedChange = { showDropDown = it },
                    ) {
                        OutlinedTextField(
                            // The `menuAnchor` modifier must be passed to the text field to handle
                            // expanding/collapsing the menu on click. A read-only text field has
                            // the anchor type `PrimaryNotEditable`.
                            modifier = Modifier
                                .animateItem()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            value = gradeForClass.gradePoint.letter,
                            onValueChange = {},
                            readOnly = true,
                            singleLine = true,
                            label = { Text("Grade") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDropDown) },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        )
                        ExposedDropdownMenu(
                            expanded = showDropDown,
                            onDismissRequest = { showDropDown = false },
                        ) {
                            GradePoint.entries.forEach { grade ->
                                DropdownMenuItem(
                                    text = { Text(grade.letter) },
                                    onClick = {
                                        viewModel.modifyGradePoint(index, grade)
                                        showDropDown = false
                                    },
                                    leadingIcon = {
                                        if (grade == gradeForClass.gradePoint) Icon(Icons.Default.Check, null)
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .animateItem()
                            .fillMaxSize()
                            .padding(top = 8.dp)
                    ) {
                        IconButton(
                            onClick = { viewModel.removeGrade(index) },
                        ) { Icon(Icons.Default.Delete, null) }
                    }
                }
            }

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                Button(
                    onClick = viewModel::addGrade,
                    modifier = Modifier.animateItem()
                ) { Text("Add Grade") }
            }
        }
    }
}

private val DoubleConverter =
    TwoWayConverter<Double, AnimationVector1D>({ AnimationVector1D(it.toFloat()) }, { it.value.toDouble() })

fun Double.roundTo(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}