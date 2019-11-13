(function(document, window, $) {

	'use strict';

	var LoginReq;
	var LoginResp;
	var Msg;

	var $loginForm = $('#loginForm');

	var $userName = $loginForm.find(':input[name="userName"]');
	var $password = $loginForm.find(':input[name="password"]');
	var $login = $loginForm.find(':input[name="login"]');

	var $chatForm = $('#chatForm');

	var $chatMessage = $chatForm.find(':input[name="chatMessage"]');
	var $send = $chatForm.find(':input[name="send"]');
	var $chatTextArea = $('#chatTextArea');

	var ws;
	var isConnected = false;

	protobuf.load("proto/LoginReq.proto", function(err, root) {
		if (err) {
			throw err;
		}
		LoginReq = root.lookupType("pers.protobuf.LoginReq");
	});

	protobuf.load("proto/LoginResp.proto", function(err, root) {
		if (err) {
			throw err;
		}
		LoginResp = root.lookupType("pers.protobuf.LoginResp");
	});

	protobuf.load("proto/Msg.proto", function(err, root) {
		if (err) {
			throw err;
		}
		Msg = root.lookupType("pers.protobuf.Msg");
	});

	$login.on('click', function(e) {

		if (!isConnected) {
			init_ws();
		}

		send_login_req();
	});

	$send.on('click', function(e) {
		send_msg();
	});

	function send_login_req() {
		var userName = $userName.val();
		var password = $password.val();

		var req = LoginReq.create();

		req.type = 1;
		req.userName = userName;
		req.password = password;

		var payload = LoginReq.encode(req).finish();
		ws.send(payload);
	}

	function send_msg() {
		var userName = $userName.val();
		var message = $chatMessage.val();

		if($.trim(message) == "") {
			return ;
		}

		$chatMessage.val("");

		var sendMsg = Msg.create();

		sendMsg.type = 3;
		sendMsg.userName = userName;
		sendMsg.msg = message;

		var mespayload = Msg.encode(sendMsg).finish();
		ws.send(mespayload);
	}

	function init_ws() {
		ws = new WebSocket("ws://localhost:8899/chat");
		ws.binaryType = 'arraybuffer';

		ws.onopen = function(evt) {
			isConnected = true;
			send_login_req();
		};

		ws.onclose = function(evt) {
			isConnected = false;
			console.log("ws onclose: " + evt);
		};

		ws.onmessage = function(evt) {

			var payload = evt.data;
			var hasError = LoginResp.verify(payload);
			if (hasError) {
				throw hasError;
			}

			var s = new Uint8Array(payload);

			/**
			 *
			 * 响应信息首字节 int32 resultCode = 1;
			 * 			  -->
			 * 			  fileld_number = 1, wire_type = 0
			 * 			  1 << 3 | 0  ==> 8
			 */

			if (s[0] != 0x08) {
				alert("undefine type");
				return;
			}

			if (s[1] == 0x02) {
				login_logic(s);
			} else if (s[1] == 0x03) {
				access_message_logic(s);
			} else {
				alert("undefine type");
			}

		};

		ws.onerror = function(evt) {
			console.log("ws onerror: " + evt);
		};
	}

	function login_logic(s) {
		var resp = LoginResp.decode(s);
		switch (resp.resultCode) {
		case 1:
			$('#chatDiv').show();
			$('#logDiv').hide();
			break;

		case 2:
			alert('fatal');
			break;

		default:
			alert('unkonw result code: ' + resp.resultCode);
			break;
		}
	}

	function access_message_logic(s) {
		var msgObj = Msg.decode(s);

		var show = "";

		if($userName.val() == msgObj.userName) {
			show += "->";
		}

		show += msgObj.userName + " : " + msgObj.msg;

		if(show != "") {
			$chatTextArea.append(show + "\n");
			$chatTextArea.scrollTop($chatTextArea[0].scrollHeight - $chatTextArea.height());
		}
	}

})(document, window, jQuery);