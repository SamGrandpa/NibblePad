# Iteration 2 Worksheet

### Paying off technical debt


1. The Listview UI design was completed in the simplest way possible which was aesthetically 
   unappealing because it was the easiest way to test functionality. It also wasn’t possible to add
   or remove an item from the list despite knowing they are features we will be implementing in 
   iteration 2. This resulted in reckless and deliberate technical debt. We paid off this technical 
   debt by replacing the standard Listview with a Custom Listview which allows us to add additional 
   features in the future if needed.

UI changes: fragment_foods.xml, fragment_restaurants.xml, list_row.xml, ic_launcher_round.xml
Logic changes: RestaurantsFragment.java, FoodsFragment.java

All of the changes in the files listed and in the commit [log](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/commit/97ac34a5c9258020a91a8e95bbe88d16e6645bc5)
here contains the code for how we payed off this technical debt.


2. One of the features we implemented in Iteration 2 is to ability to share your list of foods and
   restaurants. When working on this feature, the method `onShareClick()` was original thought to only 
   display a toast to the user when called by `onOptionsItemSelected()`. This method had to be 
   refactored so it would also send the String data to the intent which resulted in reckless 
   inadvertent technical debt because we didn't completely think through what was required of this 
   method. 
   
The commit showing these changes can be found [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/commit/001f6601fcbffcd25792af4d6b58d58f0f078980#0dd0a95b05fda52f830f9fefd77efc20d0ed7240_81_81)

### SOLID

[SOLID violation issue](https://code.cs.umanitoba.ca/winter-2022-a02/group-4/dish-project/-/issues/72) in project group 4

### Retrospective

In Iteration 1 our time estimates were accurate because the features and issues to the completed
were technically similar to work we already have experience with. As a result, for Interation 2 we
made similar estimates for the features to be completed. However, the features assigned larger for
this iteration were larger and more technically challenging but we were not aware of this until 
we began working on them resulting in more time being spent than what we had estimated.

[Time underestimated feature 1](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/7)
, [Time underestimated feature 2](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/issues/34)

We created [separate branches](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/branches/active)
for each feature branching off of `dev` for this iteration to keep the work that was assigned to 
each team member better organised. Previously, some team members worked off of the dev branch
directly and others working on a dedicated branch. For this iteration, we all became familiar with 
our branching strategy and used it as it was intended.

### Design patterns

A design pattern that we used in our project is are Singletons. We have 2 database interfaces 
(FoodPersistence and RestaurantPersistence) which are created once in the class Services. All 
interactions in the interface uses these singletons through the Class Services. And it is intialized only once.
Our use of Singltons can be viewed [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/blob/dev/app/src/main/java/comp3350/nibblepad/application/Services.java#L15-49)

### Iteration 1 Feedback Fixes

No issue was opened by the grader but some the feedback given is shown below:

![](docs/iteration1-feedback.png)

The database stubs are using String as the data type for Food and Restaurant ids. Since the IDs 
being used are "1", "2", "3", etc., there's no reason not to have them integers instead. In the 
proper database integers are used so we have changed the stubs to match that.

The changes can be seen [here](https://code.cs.umanitoba.ca/winter-2022-a02/group-5/nibble-pad/-/commit/d6b03722ef16c8568657c62f5f3518b6e45bf760)
