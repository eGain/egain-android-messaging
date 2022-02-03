# Configure SDK
The instructions in this section provide details to complete the SDK installation.

## Obtain Credentials
Credentials are used to verify you as a valid customer for the Conversation Hub. You need a unique ID (clientId) and password (clientSecret) to access the Conversation Hub. If you have not yet obtained credentials, contact you eGain representative.  

## Configure Themes
The SDK comes with the eGain's default UI theme. If you are using your own theme, a merge conflict can be avoided by adding the following to the `<application>` element in your `AndroidManifest.xml` file: 
```xml
<application>
    ...
    xmlns:tools="http://schemas.android.com/tools"
    tools:replace="android:theme">
```

## Define Android Permissions
By default, the SDK includes the **INTERNET** permission in order to make network requests.
```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

If attachments are enabled, **WRITE_EXTERNAL_STORAGE** is included for sending and receiving attachments:
```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

> ⚠️ Any app that declares the **WRITE_EXTERNAL_STORAGE** permission implicitly grants the **READ_EXTERNAL_STORAGE** permission

## Enable AndroidX
Android X is required in our SDK. Include the following in your `gradle.properties` to enable Android X.
```xml
android.useAndroidX=true
```

## Choose Default or Custom Options
There are two ways in which you can use the SDK:

- Option 1: Default version
  - Out-of-the-Box UI (default).
  - UI customization is not in current scope.
- Option 2: Customizable version:
  - This version allows you to use SDK methods and build your own UI around it.

### Option 1: Default "Out-of-the-Box"
The default, "out-of-the-box" version provided by the SDK provides basic settings that can be configured to enhance your  experience and allows customers to interact with eGain Conversation Hub using the chat button.  The chat button serves as the entry point to begin chatting with the eGain Conversation Hub.

There are two types of conversation modes you can add to the application: a customer and a guest mode. 

To begin using the SDK, add the following lines in the application to create chat button configurations for these two types of conversation modes.

#### **Customer Mode Conversation**

To create a chat button which requires users to provide their credentials to continue the chat, add the following authenticated construct to the application. 
```Java
public eGainButton(
    context,
    clientId,
    clientSecret,
    clientName,
    clientEmail,
    botGreeting);
```

##### **Example ( Customer Mode )**

The following example is based on an Android Pixel 3A which has a screen resolution of 1080x2220.

The example uses the default theme of the eGain SDK application, positions the chat button in the right corner of the screen (starting at 0.0 in the upper left corner, x = 980 and y = 1740) and sets the icon color to the "BLUE" color android supports.

For further customization, refer to how to change the theme for your application in the configuration section.

##### **Example:**
```java
eGainButton launchCustomer = new eGainButton(
    this,
    "28c0ae1a54cc4aeea6c5c272b2824da4",
    "DVIpG4guOkMRU0C-hsQ@-fUDXwSdDlcWYfMXi3cMMNtP3yLmGq@W_pMUzUn5",
    "John",
    "John@email.com",
    true);
 ```
 
#### **Guest Mode Conversation**
To create a chat button which does not require users to provide credentials to continue to the chat, add the following unauthenticated construct to the application.
```java
public eGainButton(
    context,
    clientId,
    clientSecret,
    botGreeting);
```

#### **Parameters**
| Name | Type | Description |
|----------|----------|-----------------|
| context	 | android.content.Context | Context of the current application |
| clientID | String |	eGain Conversation Hub client ID |
| clientSecret |	String |	eGain Conversation Hub client secret |
| clientName | String |	Name of the client |
| clientEmail | String	| Email address of the client |
| botGreeting	| Boolean	| An initial greeting from the bot |

#### **Screenshots**
Coming soon

### Option 2: Customized SDK
The SDK offers a set of methods which allow you to utilize the full functionality of eGain's Conversation Hub while keeping your mobile application UI and design looking and functioning the way you want. The methods listed in this section describe how to configure the SDK. They consist of methods for initiating the conversation, sending and receiving messages and attachments, and ending the conversation for both customers that require login credentials and guest users. For the provided methods, the SDK uses a Websocket API for data transfer.

#### **Overview for Customizing the SDK**
The only requirement for using the provided methods is to follow the proper setup to initialize the chat, after which the usage is up to the developer.

The general flow of the SDK begins with an initialization call to the Conversation Hub that will either validate an existing session or initialize a new one, verifying the provided credentials in the process. 
The `intialize()` method will return whether or not a valid session exists. After a new session has been created, subsequent calls to `initialize()` will return true unless 
either the user or agent ends the conversation, thereby invalidating the current session. Once a valid session has been started and while it remains valid, the SDK can use 
`sendMessage()` to send messages, `uploadAttachment()` to send files and images, `receiveMessage()` to receive messages, and `receiveAttachmentMessage()` to receive images 
and files. To end the session, `endConversation()` must be called. Note that the agent can end the conversation as well. In order to receive messages, the `receiveMessage()`
method must be observed by the developer, upon which they can define how they want to display the data. 

#### Available methods
- initialize()
- sendMessage()
- receiveMessage()
- uploadAttachment()
- receiveAttachmentMessage()
- endConversation()
   
All of the methods are included in a single class called `EgainMessaging` (com.egain.ps.sdk.EgainMessaging). It can be initialized with:
`EgainMessaging eGainMessaging = new EgainMessaging(context);`

#### Parameters - EgainMessaging
|Name |Type |Description |
|-|-|-|
|context|	android.content.Context	|Context of the current application |

SDK methods are overloaded for customer and guest mode conversations. You can use the appropriate method as per your requirement.

#### Initialize
This method acts as the entry point for the SDK and can be used to initialize the conversation. This method will require clientId and clientSecret (see obtaining credentials). It will return a boolean indicating whether there is currently a valid session or not. If the current session is invalid or has expired, it will validate the clientId and clientSecret, generating a new session if successful. A session will remain valid until endConversation() has been called, the agent ends the conversation, or if it expires after its timeout duration. 

#### Customer Mode - Initialize
```java
public boolean initialize(eGainClientId, eGainClientSecret, botGreeting, loggedUserName, emailId)
```

#### Guest Mode - Initialize
```java
public boolean initialize(eGainClientId, eGainClientSecret, botGreeting, loggedUserName)
```

#### Parameters - Initialize Chat
|Name |Type |Description |
|-|-|-|
|eGainClientId|	String|	eGain Conversation Hub client ID|
|eGainClientSecret|	String|	eGain Conversation Hub client secret|
|botGreeting|	Boolean|	Value specifying if bot should send welcome message|
|loggedUserName|	String|	Name of customer|
|emailId|	String|	Email ID of customer|

#### Send Message
This method can be used to send text messages.

#### Customer Mode - Send Message
```java
public void sendMessage(message, emailId)
```

#### Guest Mode - Send Message
```java
public void sendMessage(message)
```

#### Parameters - Send Message
|Name |Type |Description |
|-|-|-|
|message|	String|	Text message sent by customer|
|emailId|	String|	Email ID of customer|

#### Receive Message
This method can be used to receive messages. 
This method returns a MutableLiveData of type `EgainMessage` and can be observed to receive the message. 
EgainMessage's have 3 methods to receive the message type, the message content, and the agents name. 
The available message types that can be received are listed below under "Available Responses".

> **_NOTE:_**: `ReceiveMessage()` will be the same for both customer and guest mode conversations.

```java
public MutableLiveData<EgainMessage> receiveMessage()
```

##### **Exmaple - Receive Message
```java
egainChat.receiveMessage().observe(this, message -> {
            Log.d(TAG,"message type received " + message.getType());
            Log.d(TAG,"message content received " + message.getMessageContent());
            Log.d(TAG,"message agent name received " + message.getAgentName());
        });
```

Please refer to the documentation <link> for supported message types.

#### Upload Attachment
This method can be used to upload attachments.

#### Customer Mode - Upload Attachment
```java
Customer Mode - Upload Attachment
```

#### Guest Mode - Upload Attachment
```java
public void uploadAttachment(fileUri, emailId)
```

#### Parameters - Upload Attachment
|Name |Type |Description|
|-|-|-|
|fileUri|	android.net.Uri|	Uri of the file to be uploaded from device, usually found in GET_CONTENT intent|
|emailId|	String |Email ID of customer|

#### Receive Attachment
This method can be used to receive attachments. The data is stored into an `EgainDownloadFile` type which provides a `getFileName()` and a `getDownloadUrl()` method.

> **_NOTE:_** Receive attachment message will be same for customer and guest mode conversations

This method returns a `MutableLiveData` of type `EgainDownloadFile` and can be observed to receive the message.
```java
public MutableLiveData<EgainDownloadFile> receiveAttachmentMessage()
```

#### Example receive attachment
Download URL can be used to download the attachment.
```java
egainChat.receiveAttachmentMessage().observe(this, downloadFile -> {
           Log.d(TAG,"file name received "+downloadFile.getFileName());
           Log.d(TAG,"download url received "+downloadFile.getDownloadUrl());
       });
```

#### End Conversation
This method can be used to end ongoing conversation.

#### Customer Mode -  End  Conversation
```java
public void endConversation(emailId)
```

#### Guest Mode - End Conversation
```java
public void endConversation()
```

#### public void endConversation()
|Name |Type | Description
|-|-|-|
|emailId|	String	| Email ID of customer|

## Supported Response Types
Listed below are the different types of messages that can be received and their content. 
> **_NOTE:_** These are accessed by the provided `EgainMessage` methods except for received downloads which are accessed by the provided `EgainDownloadFile` methods.

### EgainMessage Type
#### Conversation started
When the first message is sent, the following message will be received:
```java
{
    "eGainMessage":"{
                        "type":"status",
                        "messageContent":"Conversation started"
                    }"
}
```

### Conversation continued
For any messages received after this, the follow will always be received:
```java
{
    "eGainMessage":"{
                        "type":"status",
                        "messageContent":"Conversation continued"
                    }"
}
```

### Text
When the bot or agent responds with a text message, the following response will be received:
```java
{
    "eGainMessage":"{
                        "type":"text",
                        "messageContent":"Hello",
                        "agentName":"ps-mobile-sdk-customer-eg-bot"
                    }"
}
```

### Listpicker
When the bot or agent responds with a message of type listpicker, the following response will be received:
```java
{
    "eGainMessage":"{
                        "type":"richMessage.listpicker",
                        "messageContent":"{"type":"list","version":"1","title":"Check latest collection ","subtitle":"Only online","list":{"multipleSelection":false,"sections":[{"multipleSelection":false,"title":"Select Answer","order":0,"items":[{"title":"Check trim T-shirt","subtitle":"Available only online order.","id":"100001","actions":[{"type":"postback"}]},{"title":"Classic trench coat","subtitle":"Available in stores and online","id":"100002","actions":[{"type":"postback"}]}]}]}},
                        "agentName":"ps-mobile-sdk-customer-eg-bot"
                    }"
}
```

### Escalation
```java
{
    "eGainMessage":"{
                        "type":"conversation.state",
                        "messageContent":"{"status":"escalated","content":""}",
                        "agentName":"system"
                    }"
}
```
### Agent starts typing
When eGain agent starts typing, the following response will be received:
```java
{
    "eGainMessage":"{
                        "type":"typing.start",
                        "messageContent":"",
                        "agentName":"name"
                    }"
}
```

### Agent stops typing
When eGain agent stops typing, the following response will be received
```java
{
    "eGainMessage":"{
                        "type":"typing.end",
                        "messageContent":"",
                        "agentName":"name"
                    }"
}
```

### Conversation ended
When the eGain agent ends the chat, the following response will be received, with which the chat can be closed on the user device.
```java
{
    "eGainMessage": {
                        "type":"text",
                        "messageContent":"agent.end.conversation",
                        "agentName":"name"
                    }
}
```

### EgainDownloadFile Type
The provided methods `getFileName()` and `getDownloadUrl()` can be used to retrieve the response.

When the agent sends any attachments, the download url will be received and can be used to download the file and display it on the device.
```java

{
    "fileName":"featured-image-MH.jpeg",
    "downloadURL":"https://egain-pse-apps-oregon-development.s3.us-west-2.amazonaws.com/mh-websocket/dev/attachments/fromMessagingHub/649de7ad-2d86-4f7c-b874-82fa863e55ae/featured-image-MH.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIARDJ5G4KVX5TVEDOT%2F20220125%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220125T220220Z&X-Amz-Expires=300&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEG4aCXVzLXdlc3Qt3073813c&X-Amz-SignedHeaders=host"
}
```

## Branding
|Name |Type |Description|
|-|-|-|
|positionX|	Integer	|The X coordinate for the button position (0 being at the leftmost side of the screen)|
|positionY|	Integer	|The Y coordinate for the button position (0 being at the uppermost side of the screen)|
|width	|Integer	|Width of the chat button|
|height	|Integer	|Height of the chat button|
|customImageResID	|Integer |ID of resource for custom chat button image (Use value 0 for our default button image)|
|color	|android.graphics.Color	|The color for the chat button image|
|theme	|android.content.res.Resources.Theme	|The theme for the SDK's UI|
