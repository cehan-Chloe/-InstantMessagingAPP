# InstantMessagingAPP

The objective of this project is to implement an instant messaging (IM) app for Android users. A client/server outline of the app has been shown in the figure below. A snapshot of the user interface on the device has been shown in the second figure.

## Functions
* When the app is invoked, it tries to connect with the Server, and many devices can connect to the same Server. As long as the app is running on the device, it tries to connect with the server once every 30 seconds, continually. If it is successful, the Status changes to “Up.” If the network connection toggles between up and down, the app must handle it. If the device is successfully connected to the Server, the Status is Up; otherwise, it is Down.

* Connected users can join a group with a known ID to form an instant messaging (IM) group. If a user has joined a group, the “Group ID is:” field shows the group name; otherwise, it shows “Not Connected.” Similarly, users can Quit from a group. The Show button shows a list of users who are actively connected to the group now.

* You can send a message to your group by typing in a message in the appropriate window and touching the Send button. A received message is displayed in the New window, along with the name/id of the sender. Alert the user by some means about a new message received by the app.

* It is possible for a user to join multiple groups
