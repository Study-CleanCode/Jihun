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

# 프로젝트 설명
이미지를 검색해서 보관함에 수집하는 안드로이드 프로젝트
검색은 키워드 하나에 이미지 검색과 동영상 검색을 동시에 수행하여 얻은 두 가지 검색 결과를 사용

이미지 검색 API - (https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-image) 의 thumbnail_url 필드

동영상 검색 API - (https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-video) 의 thumbnail 필드

두 검색 결과를 datetime필드를 이용해 정렬하여 출력(최신 순)
Fragment는 2개 사용(하단 탭을 선택하여 전환)
- 검색 결과 fragment
    - 검색어 입력
    - 검색된 이미지 리스트 표시, 각 아이템에는 이미지와 함께 날짜와 시간 표시
    - 스크롤을 통해 다음 페이지 불러오기
    - 리스트의 특정 이미지를 선택하여 '내 보관함'에 저장
    - 이미 보관된 이미지는 특별한 표시(하트)
    - 보관된 이미지를 다시 선택하여 보관함에서 제거 가능
- 내 보관함 fragment
    - 검색 결과에서 보관한 순서대로 이미지 표시
    - 보관한 이미지 리스트는 앱 재시작 후에도 보여야 함.(SharedPreference 사용)

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
