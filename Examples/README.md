# SDK Configuration for Android
The instructions in this section provide details to complete the SDK installation.

## Table of Contents
- [SDK Configuration for Android](#sdk-configuration-for-android)
  * [Configure Conversation Hub](#configure-conversation-hub)
  * [Configure Themes](#configure-themes)
  * [Define Android Permissions](#define-android-permissions)
  * [Enable Android X](#enable-android-x)
  * [Choose Default or Custom Options](#choose-default-or-custom-options)
    + [Option 1: Default "Out-of-the-Box"](#option-1-default-out-of-the-box)
    + [Branding](#branding)
    + [Option 2: Customized SDK](#option-2-customized-sdk)
      - [Overview for Customizing the SDK](#overview-for-customizing-the-sdk)
      - [Initialize chat](#initialize-chat)
      - [Send Message](#send-message)
      - [Receive Message](#receive-message)
      - [Upload Attachments](#upload-attachments)
      - [Download Attachments](#download-attachments)
      - [End Conversation](#end-conversation)
  * [Supported Message Types](#supported-message-types)


## Configure Conversation Hub
Please contact eGain customer representative to get the Conversation Hub tenant clientId and clientSecret. 
Configure your Conversation Hub tenant using the steps listed under the section **Bring-Your-Own-Channel with eGain Virtual Assistant steps** and using this postman script **BYOC with eGain VA Conversation API Setup.postman_collection.json** from [here](https://ebrain.egain.com/kb/devcentral/content/EASY-8283/Bring-Your-Own-Channel).

While configuring Conversation Hub pass the below values in the postman script
- customer authentication - username:nmanoharan / password:egain@123
- customer callback - https://qa-napsapps.egeng.info/mh-websocket/system/egain/customer/callback

Once the configuration is complete, the following values are required to be passed to the mobile sdk
- clientId and clientSecret generated after the creation of client app
- channel type used while creating the channel
- account address used while creating the account 

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

## Enable Android X
Android X is required in our SDK. Include the following in your `gradle.properties` to enable Android X.
```xml
android.useAndroidX=true
```

## Choose Default or Custom Options
There are two ways in which you can use the SDK:

- Option 1: Default version
  - Out-of-the-Box UI (default).
  - UI customization is not in current scope.
- Option 2: Customizable version
  - This version allows you to use SDK methods and build your own UI around it.

### Option 1: Default "Out-of-the-Box"
The default, "out-of-the-box" version provided by the SDK provides basic settings that can be configured to enhance your experience and allows customers to interact with eGain Conversation Hub. eGainMessaging provides a default chat button which can be added to any view of your application. The chat button serves as the entry point to begin chatting with the eGain Conversation Hub.

There are two types of conversation modes you can add to the application: a customer and a guest mode.

>**_NOTE:_** If you do not want a bot greeting, you can leave out the "botGreeting" field when creating a new button. 

#### Customer Mode Conversation

To create a chat button that requires users to provide their credentials to continue the chat, add the following authenticated construct to the application. 
```Java
eGainMessaging launchCustomer = new eGainMessaging(
    context: "this",
    clientId: "XXXXXX",
    clientSecret: "XXXXXX",
    channelType: "mobile_sdk",
    accountAddress: "mobile_sdk_address_with_egain_bot",
    userName: "name",
    emailId: "name@email.com",
    botGreeting: "Hello, how may I help you?");
```
 
#### **Guest Mode Conversation**
To create a chat button that does not require users to provide credentials to continue to the chat, add the following unauthenticated construct to the application.
```java
eGainMessaging launchGuest = new eGainMessaging(
    context: "this",
    clientId: "XXXXXX",
    clientSecret: "XXXXXX",
    channelType: "mobile_sdk",
    accountAddress: "mobile_sdk_address_with_egain_bot",
    botGreeting: "Hello, how may I help you?");
```

#### **Parameters**
| Name | Type | Description |
|----------|----------|-----------------|
| context	 | android.content.Context | Context of the current application |
| clientId | String |	eGain Conversation Hub client ID |
| clientSecret |	String |	eGain Conversation Hub client secret |
| channelType | String | Configured channel for Conversation Hub Bot |
| accountAddress | String | Configured address for Conversation Hub Bot |
| userName | String |	Name of the client |
| emailId | String	| Email address of the client |
| botGreeting	| String | Initial greeting to be sent by the bot |

### Branding
The UI of the SDK can be customized to configure the colors, text, and text sizes that are preferred. Use the provided `branding.xml` file to see what can be changed and how to implement those changes.

1. Download the branding.xml file from the repository.
2. Add this file to your project's resource directory.
3. In this file, use the provided styles to change each view to the desired values. 
4. To choose between the different themes, use the `brandingUtil.setTheme()` method. Use 0 to keep the default UI and 1 to change to the customized UI. 

**brandingUtil.setTheme(int theme)**
|Name|Type|Description|
|-|-|-|
|theme|integer|The value for which theme to apply to the SDK. 0 for default UI, 1 for custom UI.|

#### Sample Branding Flow
![branded_flow_android](https://user-images.githubusercontent.com/94654299/154162100-36796e20-27a7-4aa8-bf36-cc6f510202fa.png)

### Option 2: Customized SDK
The SDK offers a set of methods which allow you to utilize the full functionality of eGain's Conversation Hub while keeping your mobile application UI and design looking and functioning the way you want. The methods listed in this section describe how to configure the SDK. They consist of methods for initiating the conversation, sending and receiving messages and attachments, and ending the conversation for both customers that require login credentials and guest users. For the provided methods, the SDK uses a Websocket API for data transfer.

#### Overview for Customizing the SDK
The only requirement for using the provided methods is to follow the proper setup to initialize the chat, after which the usage is up to the developer.

The general flow of the SDK begins with an initialization call to the Conversation Hub that either validates an existing session or initializes a new one. This process verifies the provided credentials. After calling the `initialize()` method, a response is sent back providing a sessionId.

After a new session is created, subsequent calls to `initialize()` returns the same sessionId in the response unless the user or agent ends the conversation, thereby invalidating the current session. When a valid session is started and while it remains valid, the SDK can use the following calls:

- `sendMessage()` to send messages
-  `upload()` to send files and images
- `receiveMessage()` to receive messages
- `download()` to receive images and files.
To end the session, call endConversation(). The agent can also end the conversation as well.

To receive messages, the `receiveMessage()` method must be observed by the developer. They can define how they want to display the data.  

**Available Methods**

- initialize()
- sendMessage()
- receiveMessage()
- upload()
- download()
- endConversation()
   
All of the methods are included in a single class called `eGainMessaging` (com.egain.ps.sdk.eGainMessaging). It can be initialized with:
`eGainMessaging eGainMessaging = new eGainMessaging(context);`

#### Parameters - eGainMessaging
|Name |Type |Description |
|-|-|-|
|context|	android.content.Context	|Context of the current application |

SDK methods are overloaded for customer and guest mode conversations. You can use the appropriate method as per your requirement.

#### Initialize chat
This method acts as the entry point for the SDK and can be used to initialize the conversation. This method requires clientId and clientSecret (see obtaining credentials). It prompts a response that provides a sessionId immediately back. If the current session is invalid or has expired, it validates the clientId and clientSecret and generates a new session if successful. A session remains valid until `endConversation()` is called, the agent ends the conversation, or if it expires after its timeout duration.

> **_NOTE:_**: If you do not want a bot greeting, you can leave out the botGreeting field when calling `initialize()`.

#### Customer Mode - Initialize Chat
```java
eGainMessaging.initialize(
    eGainClientId: "XXXXXX",
    eGainClientSecret: "XXXXXX",
    channelType: "mobile_sdk",
    accountAddress: "mobile_sdk_address_with_egain_bot",
    botGreeting: "Hello, how may I help you?",
    userName: "userName",
    emailId: "userName@email.com");
```

#### Guest Mode - Initialize Chat
```java
eGainMessaging.initialize(
    eGainClientId: "XXXXXX",
    eGainClientSecret: "XXXXXX",
    channelType: "mobile_sdk",
    accountAddress: "mobile_sdk_address_with_egain_bot",
    botGreeting: "Hello, how may I help you?");
```

#### Parameters - Initialize Chat
|Name |Type |Description |
|-|-|-|
|eGainClientId|	String|	eGain Conversation Hub client ID|
|eGainClientSecret|	String|	eGain Conversation Hub client secret|
|channelType |String |Configured channel for Conversation Hub bot |
|accountAddress |String |Configured address for Conversation Hub bot |
|botGreeting|	Boolean|	Value specifying if bot should send welcome message|
|userName|	String|	Name of customer|
|emailId|	String|	Email address of customer|

#### Responses - Initialize Chat
The sessionId is returned synchronously after the initialize call and stored in the SDK internally. These are the possible responses, indicating if a valid session was created or not and can be observed in the receiveMessage method.

When chat is initialized, but conversation has not started.
```java
{
    "sessionId": "bb1e960c-0227-447c-a675-0e4e2fa5c318",
    "status": "Conversation not started"
}
```
When chat is initialized and conversation has started.
```java
{
    "sessionId": "bb1e960c-0227-447c-a675-0e4e2fa5c318",
    "status": "Conversation started"
}
```

#### Send Message
This method can be used to send text messages.

#### Customer Mode - Send Message
```java
eGainMessaging.sendMessage(
    message: "hi",
    email: "name@email.com");
```

#### Guest Mode - Send Message
```java
eGainMessaging.sendMessage(
    message: "hi");
```

#### Parameters - Send Message
|Name |Type |Description |
|-|-|-|
|message|	String|	Text message sent by customer|
|email|	String|	Email address of customer|

#### Responses - Send Message
These responses are synchronous to the `sendMessage` call. 

When the first message is sent, the following message is received:
```java
{
	"status":"Conversation started",
	"authorization":"bb1e960c-0227-447c-a675-0e4e2fa5c318"
}
```
For any messages received after this, the follow is received.
```java
{
	"status":"Conversation continued",
	"authorization":"bb1e960c-0227-447c-a675-0e4e2fa5c318"
}
```
If the agent has already closed the conversation or the system has already closed the conversation, the following response in received
```java
{
	"status": "Invalid sessionId",
	"authorization": "413ff6be-146d-4c78-9ffa-2de15d2c24e1"
}
```

#### Receive Message
This method is used to receive **ALL** message types except attachments. This method returns a [MutableLiveData](https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData) of type `EgainMessage` and can be observed to receive the message. EgainMessage's have 3 methods to receive the message type, the message content, and the agents name. The available message types that can be received are listed under "Responses".

> **_NOTE:_**: `ReceiveMessage()` is the same for both customer and guest mode conversations.

```java
public MutableLiveData<EgainMessage> receiveMessage()
```

##### Example - Receive Message
```java
egainMessaging.receiveMessage().observe(this, message -> {
            Log.d(TAG,"message type received " + message.getType());
            Log.d(TAG,"message content received " + message.getMessageContent());
            Log.d(TAG,"message agent name received " + message.getAgentName());
        });
```

#### Responses - Receive Message
Please refer to the [document](#supported-response-types) for supported message types.


#### Upload Attachments
This method can be used to upload attachments.

#### Customer Mode - Upload Attachments
```java
eGainMessaging.upload(
    fileUri: "fileUri",
    email: "name@email.com");
```

#### Guest Mode - Upload Attachments
```java
eGainMessaging.upload(
    fileUri: "fileUri");
```

#### Parameters - Upload Attachments
|Name |Type |Description|
|-|-|-|
|fileUri|	android.net.Uri|	Uri of the file to be uploaded from device, usually found in GET_CONTENT intent|
|email|	String |Email address of customer|

#### Responses - Upload
A S3 pre-signed URL is received as a response. Upload the corresponding file to this URL, which is then uploaded to the agent.
```java
{
    "status": "Conversation continued",
    "fileName": "filename.extension",
    "uploadURL": "https://egain-pse-apps-oregon-development.s3.us-west-2.amazonaws.com/mh-websocket/dev/attachments/toMessagingHub/a22db903-7654-4c1d-9f83-dbd6bc7d3249/hello?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIARDJ5G4KV4RZO4XXU%2F20220122%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220122T231612Z&X-Amz-Expires=300&X-Amz-Security-Token=IQoJb3JpZ2luX2VjECcaCXVzLXdlc3QtMiJHMEUCIHCCAHV%2BZak0%2FHWRzbZrJrzPTY9aqYnaNAFvmMw4k9n%2FAiEAg380MNn3I2XhujMZlxfGq3%2FRhQaDL3FvGL6NBIFpcqcqrAIIUBABGgwwNzU4MjcxNzYxMDciDHiB4fDMyVZm18ZkjiqJAs%2BsDZ0Q537LqWOxpyRIogD9PQzlZ9TWGKQ5c%2Fj4M%2BsfPwIelYaoXwhT%2BgO2ByQKpjhYwhDgKnyiB3qOEA1mWI%2Ft2p3gmKBOvQn96MqQUfQumAv6NVKx6BOlw32tRJQexnifR7SfO6oT71625q68VQUWLzd54j5sRCKwxmgYPhIk1ggQQhjpUme00zEPhEfomlBk4gTklXpitWPrVU8pOWAzmAWyEhyuRpxoBlz%2BiQ8tTGa9YbyKusX0Dd1FE77N3jpCbLD3Rskr%2Be49KzoCG9BLG0YySraQV3ZIpE1PmXU5M3Casre9%2FUb5m2aC3X2vf3JkKGIxcGsjziXktUEyqUsksV%2FaLcdge54w9ZuyjwY6mgFHUEi12RaC2JT2qKTaEcdFYTUtVk3pwkCR%2B6Xzsq0TJGfxE%2FWk6hPEx0oKX9%2FEd8dHt5N1RrIUw%2FPD98Makq%2BILBUZZRi6dgOWvgVzLogKcIFzt%2FP6MMj8vIR9FQG4bV%2Biw2F7rfs%2FLigOIVcKc7jfcdq82AspIwIyYEumDlUYUduep36Kbv2kS%2B9df7FWKxhOOwUr3XOe1XM3&X-Amz-Signature=b0e37cbe2b8e512a150a7cdae32551e96fddf1e93286363db604e84043a274f0&X-Amz-SignedHeaders=host",
    "authorization": "bb1e960c-0227-447c-a675-0e4e2fa5c318"
}
```

#### Download Attachments
This method is used to receive attachments. The data is stored in an `EgainDownloadFile` type which provides a `getFileName()` and a `getDownloadUrl()` method.

> **_NOTE:_** Receive attachment message is same for customer and guest mode conversations.

This method returns a [MutableLiveData](https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData) of type `EgainDownloadFile` and can be observed to receive the message.
```java
public MutableLiveData<EgainDownloadFile> receiveAttachmentMessage()
```

#### Example - Download Attachments
The following is the Download URL which can be used to download the attachment.
```java
{
    "status": "Conversation continued",
    "fileName": "Downloadtest.jpeg",
    "downloadURL": "https://egain-pse-apps-oregon-development.s3.us-west-2.amazonaws.com/mh-websocket/dev/attachments/fromMessagingHub/72b73502-0b89-41d7-bc43-c04a6864282a/Downloadtest.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIARDJ5G4KVQ3X2365G%2F20220215%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220215T003009Z&X-Amz-Expires=300&X-Amz-Security- Token=IQoJb3JpZ2luX2VjEFEaCXVzLXdlc3QtMiJHMEUCIGycvWZueJkgGfirnuu7JeXiXtEdMgOVs1UD5qmLlNYgAiEAr0jRUzGTI%2BecLIwHx3w8MZsKT6BCZg%2BGKOaWTwEp6P4quAIImv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgwwNzU4MjcxNzYxMDciDEVcWDCu1t4KgE9jyiqMAnkAiixxgVc6cE4WXMxhKo7MrSpNK9UFt0Cj6YuCAsjyPtTm5g4HP65I7cMKxEZz%2BXx%2B5MbEAXWyPnzoyurEIi3KI2Uj2NZrVwPwBvnBCDJF00VdbQ8yItIXuzuTdctAD5kI4gx0ZyTC0E2FHHsC7ImCemcdWO%2F85Avq419rRIJo18vpOsy45WEGs5wjGTfPBNKDXeC2ncjD5LxjE1drY8Mb3nIEPVzXfx5BOk4co2YU7oRcbKUwF0qJ7Ly%2Fo64tyW8tDEH0PL%2Frl7XWGbK9sE5CTzKyLDENCyqg%2BoR0C3uS7wy6aNkWIUUazOo1socIeeBUBVD2PRj%2Fm6%2Bf43Rnjiq%2Fp1prxh4h3YtLP6gwyearkAY6mgF5Jt4V%2FDeXPDF%2F%2FBYlUp%2F8kfQjvIx5CqnTvydD7%2B1e4KabqbPqUndjHMkmCPif5L6dgOD87yc176LYD5OVog4LgC4Gehm1nb63QRaLaMNnK5e9FP2vgGzpGc79B65L7MLYx%2FUFWcHEKu%2FmUrS0pHjh2jthN4xHalQ9CI4%2BAfKob%2BA9M8ROdoP2xV%2FZ5xedFSOsOJVRWJhJr7K8&X-Amz-Signature=a7d8e51983d012bca238d7d265412c8a5b975dc59da9b459c3590094c778e722&X-Amz-SignedHeaders=host",
    "authorization": "bb1e960c-0227-447c-a675-0e4e2fa5c318"
}
```

#### End Conversation
This method can be used to end ongoing conversation.

#### Customer Mode - End Conversation
```java
eGainMessaging.endConversation(
    email: "name@email.com");
```

#### Guest Mode - End Conversation
```java
eGainMessaging.endConversation();
```

#### public void endConversation()
|Name |Type | Description
|-|-|-|
|email|	String	| Email address of customer|

#### Responses - End Conversation
When the `endConversation()` method is called, the following is received.
```java
{
	"status": "Conversation ended",
	"authorization": "dbe63d6c-0097-4e50-b035-905ca098cef2"
}
```

## Supported Message Types
Listed below are the different types of messages that can be received and their content. 
> **_NOTE:_** These are accessed by the provided `eGainMessage` methods except for received downloads which are accessed by the provided `eGainDownloadFile` methods.

### EgainMessage Type

### Text
When the bot or agent responds with a text message, the following response is received.
```java
{
    "eGainMessage": {
        "type": "text",
        "messageContent": "Hello",
        "agentName": "ps-mobile-sdk-customer-eg-bot"
    },
    "authorization": "45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### Listpicker
When the bot or agent responds with a message of type listpicker, the following response is received.
```java
{
    "eGainMessage": {
        "type": "richMessage.listpicker",
        "messageContent": "{
            "type": "list",
            "version": "1",
            "title": "Check latest collection",
            "subtitle": "Only online",
            "list": {
                "multipleSelection": "false",
                "sections": [{
                            "multipleSelection": false,
                            "title": "Select Answer",
                            "order": 0,
                            "items": [{
                                "title": "Check trim T-shirt",
                                "subtitle": "Available only online order.",
                                "id": "100001",
                                "actions": [{
                                    "type": "postback"
                            }]},
                            {"title":"Classic trench coat",
                            "subtitle": "Available in stores and online",
                            "id": "100002",
                            "actions": [{
                                "type":"postback"
            }]}]}]}},
            "agentName": "ps-mobile-sdk-customer-eg-bot"
        },
     "authorization": "45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### Richlink
When the bot or agent responds with a message of type richlink, the following response is received.
```java
{
	"eGainMessage": {
			"type":"richMessage.richlink",
			"messageContent":"{
					"version":"1",
					"type":"web_url",
					"imageid":"1",
					"images":[{
							"title":"Winter Jackets!!",
							"url":"https://images-na.ssl-images-amazon.com/images/I/41yy1%2B08agL._SR38,50_.jpg",
					   		"link":"https://aznadestzwa07.egdemo.info/purplenile/collection.html",
							"imageid":"1",
							"mimeType":"image/jpg",
							"style":"icon"}]
					}
			},
	"authorization":"45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### Escalation
```java
{
	"eGainMessage":{
			"type":"conversation.state",
			"messageContent":"{
					"status":"escalated",
					"content":""}",
					"agentName":"system"
			},
	"authorization":"45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```
### Agent starts typing
When eGain agent starts typing, the following response is received.
```java
{
	"eGainMessage":{
			"type":"typing.start",
			"messageContent":"",
			"agentName":"name"
			},
	"authorization":"45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### Agent stops typing
When eGain agent stops typing, the following response is received.
```java
{
	"eGainMessage":{
			"type":"typing.end",
			"messageContent":"",
			"agentName":"name"
			},
	"authorization":"45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### Conversation ended
When the eGain agent ends the chat, the following response is received, with which the chat can be closed on the user device.
```java
{
	"eGainMessage": {
			"type":"text",
			"messageContent":"agent.end.conversation",
			"agentName":"name"
			},
	"authorization":"45e1bcea-a4c0-4c0b-a4d7-63feb1d7ee3c"
}
```

### EgainDownloadFile Type
The provided methods `getFileName()` and `getDownloadUrl()` can be used to retrieve the response.

When the agent sends any attachments, the download URL is received and can be used to download the file and display it on the device.
```java
{
	"status":"Conversation continued",
	"fileName":"Downloadtest.jpeg",
	"downloadURL":"https://egain-pse-apps-oregon-development.s3.us-west-2.amazonaws.com/mh-websocket/dev/attachments/fromMessagingHub/72b73502-0b89-41d7-bc43-c04a6864282a/Downloadtest.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIARDJ5G4KVQ3X2365G%2F20220215%2Fus-west-2%2Fs3%2Faws4_request&X-Amz-Date=20220215T003009Z&X-Amz-Expires=300&X-Amz-Security- Token=IQoJb3JpZ2luX2VjEFEaCXVzLXdlc3QtMiJHMEUCIGycvWZueJkgGfirnuu7JeXiXtEdMgOVs1UD5qmLlNYgAiEAr0jRUzGTI%2BecLIwHx3w8MZsKT6BCZg%2BGKOaWTwEp6P4quAIImv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FARABGgwwNzU4MjcxNzYxMDciDEVcWDCu1t4KgE9jyiqMAnkAiixxgVc6cE4WXMxhKo7MrSpNK9UFt0Cj6YuCAsjyPtTm5g4HP65I7cMKxEZz%2BXx%2B5MbEAXWyPnzoyurEIi3KI2Uj2NZrVwPwBvnBCDJF00VdbQ8yItIXuzuTdctAD5kI4gx0ZyTC0E2FHHsC7ImCemcdWO%2F85Avq419rRIJo18vpOsy45WEGs5wjGTfPBNKDXeC2ncjD5LxjE1drY8Mb3nIEPVzXfx5BOk4co2YU7oRcbKUwF0qJ7Ly%2Fo64tyW8tDEH0PL%2Frl7XWGbK9sE5CTzKyLDENCyqg%2BoR0C3uS7wy6aNkWIUUazOo1socIeeBUBVD2PRj%2Fm6%2Bf43Rnjiq%2Fp1prxh4h3YtLP6gwyearkAY6mgF5Jt4V%2FDeXPDF%2F%2FBYlUp%2F8kfQjvIx5CqnTvydD7%2B1e4KabqbPqUndjHMkmCPif5L6dgOD87yc176LYD5OVog4LgC4Gehm1nb63QRaLaMNnK5e9FP2vgGzpGc79B65L7MLYx%2FUFWcHEKu%2FmUrS0pHjh2jthN4xHalQ9CI4%2BAfKob%2BA9M8ROdoP2xV%2FZ5xedFSOsOJVRWJhJr7K8&X-Amz-Signature=a7d8e51983d012bca238d7d265412c8a5b975dc59da9b459c3590094c778e722&X-Amz-SignedHeaders=host",
	"authorization":"bb1e960c-0227-447c-a675-0e4e2fa5c318"
}
```
