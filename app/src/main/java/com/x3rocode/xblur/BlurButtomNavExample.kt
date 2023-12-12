package com.x3rocode.xblur

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun TestBlurBottomNav(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { MyBottomNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun MyBottomNavigation(navController: NavHostController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Favorite,
        BottomNavItem.Setting,
        BottomNavItem.MyPage
    )
    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { item.icon },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
                selectedContentColor = Color.DarkGray,
                unselectedContentColor = Gray,
                selected = currentRoute == item.screenRoute,
                alwaysShowLabel = false,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun testHomeScreen(){
    testScreen(text = R.string.text_home, backgroundImg = R.drawable.test1)
}

@Composable
fun testFavoriteScreen(){
    testScreen(text = R.string.text_favorite, backgroundImg = R.drawable.test2)
}

@Composable
fun testSettingScreen(){
    testScreen(text = R.string.text_setting, backgroundImg = R.drawable.test3)
}

@Composable
fun testMyPageScreen(){
    testScreen(text = R.string.text_mypage, backgroundImg = R.drawable.test4)
}

@Composable
fun testScreen(
    text : Int,
    backgroundImg : Int
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = backgroundImg),
                contentScale = ContentScale.FillBounds)
    ) {
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

const val HOME = "HOME"
const val FAVORITE = "FAVORITE"
const val SETTING = "SETTING"
const val MYPAGE = "MYPAGE"

sealed class BottomNavItem(
    val title: Int, val icon: ImageVector, val screenRoute: String
) {
    object Home : BottomNavItem(R.string.text_home, Icons.Default.Home, HOME)
    object Favorite : BottomNavItem(R.string.text_favorite, Icons.Default.Star, FAVORITE)
    object Setting : BottomNavItem(R.string.text_setting, Icons.Default.Settings, SETTING)
    object MyPage : BottomNavItem(R.string.text_mypage, Icons.Default.Person, MYPAGE)
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.screenRoute, ) {
        composable(BottomNavItem.Home.screenRoute) {
            testHomeScreen()
        }
        composable(BottomNavItem.Favorite.screenRoute) {
            testFavoriteScreen()
        }
        composable(BottomNavItem.Setting.screenRoute) {
            testSettingScreen()
        }
        composable(BottomNavItem.MyPage.screenRoute) {
            testMyPageScreen()
        }
    }
}