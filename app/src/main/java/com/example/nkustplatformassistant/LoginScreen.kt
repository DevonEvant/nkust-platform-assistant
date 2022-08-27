@file:Suppress("SpellCheckingInspection")

package com.example.nkustplatformassistant

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.nkustplatformassistant.data.remote.FetchData
import com.example.nkustplatformassistant.ui.theme.Nkust_platform_assistantTheme
import kotlinx.coroutines.launch

val user = FetchData()

class LoginScreen : ComponentActivity() {
    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent() {
            val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
                throw Error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner")
            }
            val loginParamsViewModel: LoginParamsViewModel =
                viewModel(viewModelStoreOwner = viewModelStoreOwner)

            Nkust_platform_assistantTheme {

                // navController As start destination
                navController = rememberNavController()
//                NkustpaNavGraph(navController = navController)
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreenBase(loginParamsViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        throw Error("No ViewModelStoreOwner was provided via LocalViewModelStoreOwner")
    }
    val loginParamsViewModel: LoginParamsViewModel =
        viewModel(viewModelStoreOwner = viewModelStoreOwner)
    Nkust_platform_assistantTheme {
        LoginScreenBase(loginParamsViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginWhateverPreview() {
    Nkust_platform_assistantTheme() {
        Column() {
            ShowDialogBase(showDialog = remember { mutableStateOf(true) })
        }
    }
}


// LoginParams State hosting
class LoginParamsViewModel : ViewModel() {
    private val _uid: MutableLiveData<String> = MutableLiveData("")
    private val _pwd: MutableLiveData<String> = MutableLiveData("")
    private val _pwdVisibility: MutableLiveData<Boolean> = MutableLiveData(false)

    val uid: LiveData<String> = _uid
    val pwd: LiveData<String> = _pwd
    val pwdVisibility: LiveData<Boolean> = _pwdVisibility

    fun onUidChange(newUid: String) {
        _uid.value = newUid
    }

    fun onPwdChange(newPwd: String) {
        _pwd.value = newPwd
    }

    fun onPwdVisibilityReversed() {
        _pwdVisibility.value = (_pwdVisibility.value)!!.not()
    }
}

@Composable
fun LoginScreenBase(loginParamsViewModel: LoginParamsViewModel = viewModel()) {
    val uid: String by loginParamsViewModel.uid.observeAsState("")
    val pwd: String by loginParamsViewModel.pwd.observeAsState("")
    val pwdVisibility: Boolean by loginParamsViewModel.pwdVisibility.observeAsState(false)
    // To pass viewmodel between composable, we use viewModelStoreOwner and
    // add a loginParamsViewModel4 to showDialogBase
    LoginForm(
        uid = uid, pwd = pwd, pwdVisibility = pwdVisibility,
        onUidChanged = { loginParamsViewModel.onUidChange(it) },
        onPwdChanged = { loginParamsViewModel.onPwdChange(it) },
        onPwdVisibilityReversed = { loginParamsViewModel.onPwdVisibilityReversed() },
        loginParamsViewModel = loginParamsViewModel,
    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoginForm(
//    navController: NavController,
    uid: String,
    pwd: String,
    pwdVisibility: Boolean,
    onUidChanged: (String) -> Unit,
    onPwdChanged: (String) -> Unit,
    onPwdVisibilityReversed: () -> Unit,
    loginParamsViewModel: LoginParamsViewModel,
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    ShowDialogBase(showDialog, etxtCodeViewModel = EtxtCodeViewModel(
        loginParamsViewModel
    ))

    // user.loginWebap(uid, pwd, etxtCode)
    // navController.navigate(
    // route = Screen.Home.route,
    // )
    // TODO(1. only put etxt to home screen and do login in HomeScreen - bad
    //      2. create data class to determinate is it okay to logon
    //         if it's OK, then direct put Home screen
    //      3. use viewmodel to handling uid, pwd & etxt, then login in HomeScreen - meh)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!",
            style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        OutlinedTextField(
            value = uid,
            onValueChange = onUidChanged,
            label = { Text(text = "Student ID") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.TwoTone.Person,
                    "")
            },
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .focusTarget()
                .fillMaxWidth(0.85F),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(
                onGo = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
            )
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.85F),
            value = pwd,
            onValueChange = onPwdChanged,
            label = { Text(text = "Password") },
            singleLine = true,
            leadingIcon = {
                Icon(imageVector = Icons.TwoTone.Password,
                    "")
            },
            shape = RoundedCornerShape(50),
            visualTransformation = if (pwdVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    // TODO: If auto etxt implement is complete
                    //       then check and login here
                }
            ), trailingIcon = {
                val image =
                    if (pwdVisibility) Icons.TwoTone.Visibility
                    else Icons.TwoTone.VisibilityOff
                val description =
                    if (pwdVisibility) "Hide password"
                    else "Show password"
                IconButton(onClick = onPwdVisibilityReversed) {
                    Icon(imageVector = image, description)
                }
            }
        )
        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    // TODO: Check if the user entered uid and pwd using better way
                    if (pwd.isEmpty() || uid.isEmpty()) {
                        Toast.makeText(context,
                            "ID or PW didn't input",
                            Toast.LENGTH_LONG).show()
                    } else {
                        showDialog.value = true
                    }
                },
                shape = RoundedCornerShape(50),
            ) {
                Text(text = "Login")
                Icon(imageVector = Icons.TwoTone.Login,
                    contentDescription = null,
                    Modifier.padding(start = 10.dp))
            }
        }
    }
}

// EtxtCode State Hosting
class EtxtCodeViewModel(
    // Please pass viewModel here or it'll create an new instance!
    private val loginParmsViewModel: LoginParamsViewModel,
) : ViewModel() {
    private val _imageBitmap = MutableLiveData<ImageBitmap>()
    private val _etxtcode: MutableLiveData<String> = MutableLiveData("")

    val etxtImageBitmap: LiveData<ImageBitmap> = _imageBitmap
    val etxtCode: LiveData<String> = _etxtcode

    private var stateOfLogin: Boolean = false

    init {
        requestEtxtImageBitmap()
    }

    fun requestEtxtImageBitmap() {
        viewModelScope.launch {
            onEtxtImageBitmapChange(user.getWebapEtxtBitmap())
        }
    }

    private fun onEtxtImageBitmapChange(newEtxtImageBitmap: ImageBitmap) {
        _imageBitmap.value = newEtxtImageBitmap
    }

    fun onEtxtCodeChange(newEtxtCode: String) {
        _etxtcode.value = newEtxtCode
    }

    // TODO: rewrite it to other method of Corouting scope
    fun loginForResult(): Boolean {
        viewModelScope.launch {
            stateOfLogin = user.loginWebap(
                loginParmsViewModel.uid.value!!,
                loginParmsViewModel.pwd.value!!,
                etxtCode.value!!)
        }
        return stateOfLogin
    }
}

@Composable
fun ShowDialogBase(
    showDialog: MutableState<Boolean>,
    context: Context = LocalContext.current,
    etxtCodeViewModel: EtxtCodeViewModel = EtxtCodeViewModel(
        loginParmsViewModel = LoginParamsViewModel()),
) {

    val etxtCode: String by etxtCodeViewModel.etxtCode.observeAsState("")
    val etxtImageBitmap: ImageBitmap by etxtCodeViewModel.etxtImageBitmap
        .observeAsState(ImageBitmap(width = 85, height = 40))

    fun onImageClicked() {
        etxtCodeViewModel.requestEtxtImageBitmap()
    }

    fun onPositiveCallback() {
        when {
            etxtCode.length < 4 -> {
                Toast.makeText(context, "Check validate code and enter again!", Toast.LENGTH_LONG)
                    .show()
            }
            etxtCode.isEmpty() -> {
                Toast.makeText(context, "Enter validate code before login!", Toast.LENGTH_LONG)
                    .show()
            }
            etxtCode.length == 4 -> {
                Toast.makeText(context, "Checking...", Toast.LENGTH_LONG)
                    .show()

                // TODO: Login! and start new intent
                Log.v("Login", etxtCodeViewModel.loginForResult().toString())
                showDialog.value = false
            }
        }

    }

    fun onNegativeCallback() {
        showDialog.value = false
    }
    if (showDialog.value)
        AlertDialogForEtxtCode(
            etxtCode = etxtCode,
            etxtImageBitmap = etxtImageBitmap,
            onEtxtCodeChange = { etxtCodeViewModel.onEtxtCodeChange(it) },
            onImageClicked = { onImageClicked() },
            onPositiveClick = { onPositiveCallback() },
            onNegativeClick = { onNegativeCallback() })

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressLint("CoroutineCreationDuringComposition")
fun AlertDialogForEtxtCode(
    etxtCode: String,
    etxtImageBitmap: ImageBitmap,
    onEtxtCodeChange: (String) -> Unit,
    onImageClicked: () -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    // https://stackoverflow.com/questions/68639232/jetpack-compose-how-to-create-an-imagebitmap-with-specific-size-and-configuratio
    Dialog(onDismissRequest = {}) {
        OutlinedCard(
            elevation = CardDefaults.outlinedCardElevation(15.dp),
            modifier = Modifier.aspectRatio(1F))
        {
            // 應該要交給observer處理
            if (etxtImageBitmap != ImageBitmap(width = 85, height = 40)) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Please input validate code below:",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    Image(
                        bitmap = etxtImageBitmap,
                        contentDescription = null,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(5F)
                            .clickable { onImageClicked.invoke() },
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    OutlinedTextField(
                        value = etxtCode,
                        onValueChange = { onEtxtCodeChange(it) },
                        label = { Text(text = "Validate Code") },
                        singleLine = true,
                        leadingIcon = {
                            Icon(imageVector = Icons.TwoTone.HowToReg,
                                "")
                        },
                        shape = RoundedCornerShape(50),
                        modifier = Modifier
                            .focusTarget()
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password),
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                            onPositiveClick.invoke()
                        })
                    )
                    Spacer(modifier = Modifier.padding(5.dp))
                    //button
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(onClick = {
                            onNegativeClick.invoke()
                        }) {
                            Icon(imageVector = Icons.TwoTone.Cancel,
                                modifier = Modifier.padding(end = 4.dp),
                                contentDescription = null)
                            Text(text = "Cancel")
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        OutlinedButton(
                            onClick = onPositiveClick,
                            modifier = Modifier.padding(end = 4.dp)) {
                            Icon(imageVector = Icons.TwoTone.Verified,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 4.dp))
                            Text(text = "Login")
                        }
                    }
                }
            } else {
                Column() {
                    Text(text = "Please wait...", fontSize = 20.sp)
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                        // TODO: 讓他有用
                    }
                }
            }
        }
    }
}
