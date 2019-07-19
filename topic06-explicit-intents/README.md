# Explicit Intents & Data Exchange between activities
* Explicit Intents are used when you explicitly name the intended Activity
* Implicit Intents are used when you let Android determine the intended Activity or app(s)

*These are separate repos, may be easier to import them separately in your ide.*

Data exchange using intents and results.

# IntentData1
MainActivity sends data to Activity2
# IntentData2
MainActivity sends data to Activity2<br>
Activity2 sends a result code and data back to MainActivity
# IntentData2
MainActivity sends data to Activity2 & Activity3<br>
Activity2 sends a result code and data back to MainActivity<br>
Activity3 sends a result code and data back to MainActivity<br>
Both Activity2 and Activity3 return an Intent so to determine the result origin (is it from Activity2 or Activity3) we check the request code.
# ExplicitIntentsMultiActivity  (app name Activity 1)
Multiple Implicit and Explicit intents in one app with data exchange