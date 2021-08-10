# CathayZoo

## Preview
<p>
<img src="https://user-images.githubusercontent.com/3841546/120340238-66753e80-c328-11eb-88eb-38e8195ae1f4.gif" width="270" height="540">
<img src="https://user-images.githubusercontent.com/3841546/120340543-b5bb6f00-c328-11eb-97ce-3a592fed60a5.jpg" width="270" height="540">
<img src="https://user-images.githubusercontent.com/3841546/120340546-b6540580-c328-11eb-8296-1bb598e8199e.jpg" width="270" height="540">
<img src="https://user-images.githubusercontent.com/3841546/120340529-b227e800-c328-11eb-8b0d-22bc2168c802.jpg" width="270" height="540">
<img src="https://user-images.githubusercontent.com/3841546/120340538-b3f1ab80-c328-11eb-98f8-be8eabdbd9ec.jpg" width="270" height="540">
</p>


This repository contains a sample Zoo app that implements MVVM architecture using Hilt, Room, Navigation, RxJava and Retrofit2

The app has following packages:
1. **api**: Retrofit2 interface for Zoo api
2. **data**: Model of MVVM, Dao of Room and POJO
3. **di**: Module of Hilt
4. **view**: Appication, container activity, fragments and adapters of recyclerviews
5. **viewmodel**: Viewmodel for each fragments
6. **utils**: Utility classes


### Several details.

- For supporting offline useing and smoother user UX, requseting data from DB and WebAPI at the same time. Getting from DB first (for cache data), then fetching from WebAPI and save result to DB for reducing waiting time and updating latest data every time. For guaranteeing the order of ObservableSource in RxJava, using concat is better than merge, and using **concatArrayEager** to work in parallel is better than concatArray which work in queue.
- To solving "javax.net.ssl.SSLHandshakeException:java.security.cert.CertPathValidatorException: Trust anchor for certification path not found" problem, implementing a custom **GlideModule** and OkHttpUrlLoader for bypassing certificate problem.
- Plant API result not quiet clean, it always return duplicate and not trimed data, so I need to set primary key to "F_Name_Ch" instead of "_id" in DB, and removing redundant result locally.
- There is no directly way to get whole plants data in specific zoo area on Plant API. Query with zoo area as keyword seems the only way.

