package application.views


class ViewsObjectPool {
    val loadingView : LoadingView by lazy {
        LoadingView()
    }
    val collectionView : CollectionView by lazy {
        CollectionView()
    }
    val consoleView : ConsoleView by lazy {
        ConsoleView()
    }
    val mapView : MapView by lazy {
        MapView()
    }
    val racoonView : RacoonView by lazy {
        RacoonView()
    }
    val settingsView : SettingsView by lazy {
        SettingsView()
    }
    val welcomeView : WelcomeView by lazy {
        WelcomeView()
    }
    val homeView : HomeView by lazy {
        HomeView()
    }
}