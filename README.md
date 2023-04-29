# KakaoBankAssignment
카카오 뱅크 사전과제

# Project Structure

<img width="20%" src="https://user-images.githubusercontent.com/70442964/212208921-7e8c1e1a-96c3-4372-93c6-594e2f070b78.png"/>

- 패키지 설명

|패키지|설명|
|------|---|
|application|SharedPreference 및 ApplicationClass 관련 파일|
|binding|바인딩 관련 파일|
|data|로컬 또는 원격 데이터 관련 폴더|
|di|의존성 주입을 위한 모듈 관련 폴더|
|domain|Domain Layer 관련 폴더|
|presentation|각 기능을 화면별로 나눈 폴더|
|util|확장함수 및 유틸 함수 관련 폴더|

<br/>

# Tech stack & Open-source libraries
- Minimum SDK level 33
- Kotlin based, Coroutines for asynchronous
- Jetpack
    - Lifecycle: Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel: Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - DataBinding: Binds UI components in your layouts to data sources in your app using a declarative format rather than programmatically.
    - Hilt: for dependency injection.
- Architecture
    - MVVM Architecture (View - DataBinding - ViewModel - Model)
    - Repository Pattern
- Retrofit2 & OkHttp3: Construct the REST APIs and paging network data.
- Timber: A logger with a small, extensible API.
- Glide : set images with URI from Network.