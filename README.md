
# TeamApp


**About App <br/>**
This App can generate Invitation URl for the Team which has certain priority (roles) assigned to members and
has limit of maximum members and supporters.

**App Functionality:<br/>**
1. Launch the App upon clicking on invite button app will generate the invitation link for the selected 
Permission level Coach, Player Coach, Player, and Supporter. <br/>
2. Upon each iteration of clicking on invite button four different test cases are handled as the responses
are mocked in this app.<br/>
3. Use cases Responses are:<br/>
 a. NORMAL_TEAM_RESPONSE -> Members and supporter limit is not full.<br/>
 b. TEAM_IS_FULL_RESPONSE -> Team is full but we can invite as supporter.<br/>
 c. SUPPORTERS_ARE_AVAILABLE_BUT_NO_OPEN_SPOT_TEAM_RESPONSE -> Can not make supporter.<br/>
 d. ZERO_SUPPORTER_LIMIT_RESPONSE -> Remove supporter option.<br/>
3. Depending on the use cases views are disable or removed from the app.<br/>
4. We can share the invitation URl QR code which will generate QR code as well as we can copy the invitation URl to the device clipboard.

**Android technologies implemented:<br/>**
1. Language - kotlin.<br/>
2. Architecture - MVVM.<br/>
3. Dependency injection using Dagger Hilt.<br/>
4. View Binding.<br/>
5. Retrofit library.<br/>
6. Used MockInterceptor to mock the responses.<br/>
7. Unit Test for Coroutine, Flow, Retrofit Response.<br/>
8. UI Testing using Espresso.<br/>

**Demo of App:<br/>**

https://user-images.githubusercontent.com/69842914/198544092-751f2af7-b972-4d68-9f26-bb4f3651c759.mp4
