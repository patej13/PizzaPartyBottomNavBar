package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


// ToDo 6: Add another level of hunger that is Hungry that is in between Medium and Very hungry

// ToDo 7: Using the ViewModel class, create a new ViewModel class called PizzaPartyViewModel as
// a subclass of ViewModel. Add the following properties to the PizzaPartyViewModel - see Brightspace

@Composable
fun PizzaPartyScreen(
    modifier: Modifier = Modifier,
    partyViewModel: PizzaPartyViewModel = viewModel()
) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        AppTitle(modifier)
        PartySize(
            numPeopleInput = partyViewModel.numPeopleInput,
            onValueChange = { partyViewModel.numPeopleInput = it },
            modifier = modifier
        )
        HungerLevelSelection(
            hungerLevel = partyViewModel.hungerLevel,
            onSelected = { partyViewModel.hungerLevel = it },
            modifier = modifier
        )
        TotalPizzas(
            totalPizzas = partyViewModel.totalPizzas,
            modifier = modifier
        )
        CalculateButton(
            onClick = { partyViewModel.calculateNumPizzas() },
            modifier = modifier
        )
    }
}
@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Text(
        text = "Pizza Party",
        fontSize = 38.sp,
        modifier = modifier.padding(bottom = 16.dp)
    )
}
@Composable
fun PartySize(
    numPeopleInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NumberField(
        labelText = "Number of people?",
        textInput = numPeopleInput,
        onValueChange = { onValueChange(it) },
        modifier = modifier.padding(bottom = 16.dp).fillMaxWidth()
    )
}
@Composable
fun HungerLevelSelection(
    hungerLevel: HungerLevel,
    onSelected: (HungerLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    val hungerItems = listOf("Light", "Medium", "Hungry","Very hungry")

    RadioGroup(
        labelText = "How hungry?",
        radioOptions = hungerItems,
        selectedOption = when (hungerLevel) {
            HungerLevel.LIGHT -> hungerItems[0]
            HungerLevel.MEDIUM -> hungerItems[1]
            HungerLevel.HUNGRY -> hungerItems[2]
            else -> hungerItems[3]
        },
        onSelected = {
            onSelected(when (it) {
                hungerItems[0] -> HungerLevel.LIGHT
                hungerItems[1] -> HungerLevel.MEDIUM
                hungerItems[2] -> HungerLevel.HUNGRY
                else -> HungerLevel.VERYHUNGRY
            })
        },
        modifier = modifier
    )
}
@Composable
fun TotalPizzas(
    totalPizzas: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Total pizzas: $totalPizzas",
        fontSize = 22.sp,
        modifier = modifier.padding(top = 16.dp, bottom = 16.dp)
    )
}
@Composable
fun CalculateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text("Calculate")
    }
}
@Composable
fun NumberField(
    labelText: String,
    textInput: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = textInput,
        onValueChange = onValueChange,
        label = { Text(labelText) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        modifier = modifier
    )
}
@Composable
fun RadioGroup(
    labelText: String,
    radioOptions: List<String>,
    selectedOption: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelectedOption: (String) -> Boolean = { selectedOption == it }

    Column {
        Text(labelText)
        radioOptions.forEach { option ->
            Row(
                modifier = modifier
                    .selectable(
                        selected = isSelectedOption(option),
                        onClick = { onSelected(option) },
                        role = Role.RadioButton
                    )
                    .padding(8.dp)
            ) {
                RadioButton(
                    selected = isSelectedOption(option),
                    onClick = null,
                    modifier = modifier.padding(end = 8.dp)
                )
                Text(
                    text = option,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}

