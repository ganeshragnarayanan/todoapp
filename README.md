# Pre-work - SimpleTodo

SimpleTodo is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Ganesh Ragavan Narayanan

Time spent: 15 hours spent in total

## User Stories

The following **required** functionality is completed:

* [*] User can **successfully add and remove items** from the todo list
* [*] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [*] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [*] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
      Please refer TaskDbHelper class in my github for the database definitions.
* [*] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
      Please refer CustomAdapter class in my code.
* [*] Add support for completion due dates for todo items (and display within listview item)
	  I started with using DatePicker, but it was occupying lot of space in the screen.  So I ended up using a string for the date.  I have
	  added support for editing this date field as well.
* [*] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
      I have implemented this using AlertDialog.  When a item needs to be edited, AlertDialog pops up to edit the selected todo item.  It has options
      to Cancel the request, Delete the todo item and Edit the todo item.
* [*] Add support for selecting the priority of each todo item (and display in listview item)
      I have used Spinner with high/medium/low values.  Please refer activity_main2.xml
* [*] Tweak the style improving the UI / UX, play with colors, images or backgrounds
	  I have used "+" image for add a todo item and added background colour.

The following **additional** features are implemented:

* [ ] List anything else that you can get done to improve the app functionality!

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/2K1zHLG.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far?
 Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:**

Android app development is lot of fun.  Especially with Android Studio, it makes it very convenient to develop apps.
RelativeLayout view group displays child views in relative positions.  With this, designing the layouts and user interfaces becomes simpler
and I can concentrate on the business logic.  I had used .NET framework earlier, but I found Android layouts and user interfaces to be more
friendly and easier to use.  With the auto completion feature provided by Android Studio it makes it much more convenient to get the 
defined variables and methods.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and
 what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method
  of the `ArrayAdapter`."

**Answer:**

Suppose you have say 1000 contacts that you want to display in the screen.  All the entries will not fit in a phone screen or even in a tab.  The
number of entries that will fit in a phone screen is different from that will fit in a tab.  So we want some mechanism to display the objects 
(numbers, strings, custom types, etc.) in the screen.  For this Adapter is used which provides the data that will be shown on a ListView, GridView, 
Spinner, etc.  ArrayAdapter is the simplest adapter because the adapter converts an ArrayList of objects into View items loaded into the 
container (ListView, GridView, etc.).  The ArrayAdapter fits in between an ArrayList (data source) and the ListView (visual representation) and configures 
array to use as the data source for the list and how to convert any given item in the array into a corresponding View object.

In my implementation, I have used Custom Adapter which uses DataModel class to provide the data.  DataModel object is populated by reading values
from SqlLite database.  The convertView parameter is a recycled instance of todo items ListView that was previously returned from getView().
The first time that getView() is  called, convertView is guaranteed to be null, as you have not yet created a View to be recycled.
When one of your Views in the list have been scrolled offscreen and are no longer visible to the user, they are removed from the ViewGroup and added
to the internal recycling bin. In my implementation getView() inflates the item_todo TextView (defined in item_todo.xml) that displays the task name
and the priority.

## Notes

Describe any challenges encountered while building the app.

I encountered the below challenges:

1. I was finding it challenging to get the MainActivity's context from the custom adapter. This was needed to open up the AlertDialog to 
   edit the items when a todo task is clicked from the MainActivity's ListView.  Then I resolved this by passing MainActivity.this to the
   Custom Adapter constructor and retrieving this context when needed.  I was needing the MainActivity's context to call a method defined
   in the MainActivity.
2. The layout that I defined in the xml file was not aligning properly.   Then I starting editing using "Design" tab of xml file to get the
   alignment properly.
3. I was getting "app closed" error when I try to run the app.  Then I looked into the stack trace and resolved the issue.
4. I tried using date time picker, but for some reason it was occupying a lot of space in the screen and I was not able to see the buttons 
   below.

## License

    Copyright [2017] [Ganesh Ragavan Narayanan]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.