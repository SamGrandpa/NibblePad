# Iteration 3 Worksheet

## What technical debt has been cleaned up?

We updated the aesthetic of the add button for adding new foods and restaurants to be more intuitive. 
Previously, it was simply a green circle which doesn't give any indication to the user what its
purpose is. We focused on functionality knowing that we would have to beautify the UI in the next
iteration. This is an example of prudent deliberate debt that we cleaned up.

The commit for this change can be found [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/commit/729a4591730a3a21ec6bbab961377605d4d291f2)

## What technical debt did you leave?

A piece of prudent deliberate technical debt that we have left behind is input validation when 
entering a new food and restaurant information. As it stands, repeated items and an 
empty fields are permitted.

## Discuss a Feature or User Story that was cut/re-prioritized

At the start of iteration 3 we changed the priority for [A note for each restaurant](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/10)
feature from low to high. When we were deciding on which 2 features to complete we remembered that we
were building a niche note taking app but had no feature that would allow users to do that. This 
was an important selling point in the overall purpose of the app and thus it was re-prioritized.

## Acceptance test/end-to-end
  Test: [/app/src/androidTest/java/comp3350/nibblepad/FoodsAcceptanceTests.java#L70](/app/src/androidTest/java/comp3350/nibblepad/FoodsAcceptanceTests.java#L70)

  We tested to make sure we can add a food through the UI, and when the food is added it is successfully added to the list and exists there. One of the main challenges was that Espresso checks exact values and it did provide functionality to test an item at the nth index of a ListView, but not whether any of the items in a listview match with a test or not.
  This was particularly needed because once we add a food, we need to make sure it is somewhere in the list, but cannot make any assumptions about where in the list that item would be because that would result in a flaky test.
  The workaround was creating a custom matcher for ListView that checks if any of the items in the listview contain the target string we are looking for as a text. You can find the custom matcher here: [/app/src/androidTest/java/comp3350/nibblepad/utils/AcceptanceTestUtils.java#L13](/app/src/androidTest/java/comp3350/nibblepad/utils/AcceptanceTestUtils.java#L13)


## Acceptance test, untestable 
  The notifications technically happen in the android operating system and not the app so Espresso did not have access to the notifications to check their content so we were not able to check if the notification is being made and possibly check its content.

## Velocity/teamwork
Our estimates from got worse from iteration 1 to iteration 2 but improved from iteration 2 to iteration
3. Tasks that we already had experience with had such as writing Java classes for objects and unit
tests were accurate or took less than the estimated time as evidenced by the 
   [Food](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/21) and
   [Restaurant](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/22)
   classes.
   However, the tasks that were new to us, specifically working on the UI components took more than 
   the estimated time ([Bottom Navgation bar](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/20)).
   
For Iteration 2, we expected it to be more technically challenging in addition to there being more project 
requirements than than the first iteration, however long we thought it would take to complete a task,
we allotted some additional time. This resulted in an overestimation because we were overly cautious.
Many of the tasks required the expected amount of time and some of the additional work required were 
not coded such as the worksheet, updating the architecture diagram and finding SOLID violations. The 
[database](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/32) and
[adding a food/restaurant time](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/35) 
are examples of accurate estimations and the [notifications](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/7) 
is one where our cautious approach was justified.

Iteration 3 had the most accurate time estimations which isn't surprising since we gained more 
insight into how long tasks take due to our increased familiarity with Android development and 
better understanding of all the project requirements for each iteration. 

   

