package cz.wayne.kmplayground.shared

import dev.icerock.moko.socket.Socket
import dev.icerock.moko.socket.SocketEvent
import dev.icerock.moko.socket.SocketOptions

class SocketIOTest {
    val privateMessagesSocket = Socket(
        endpoint = "https://private-messages.sts.pl",
        config = SocketOptions(
            queryParams = mapOf(/*"token" to "MySuperToken"*/),
            transport = SocketOptions.Transport.WEBSOCKET
        )
    ) {
        on(SocketEvent.Connect) {
            println("connect : PrivateMessages")
        }

        on(SocketEvent.Connecting) {
            println("connecting : PrivateMessages")
        }

        on(SocketEvent.Disconnect) {
            println("disconnect : PrivateMessages")
        }

        on(SocketEvent.Error) {
            println("error $it : PrivateMessages")
        }


        on(SocketEvent.Reconnect) {
            println("reconnect : PrivateMessages")
        }

        on(SocketEvent.ReconnectAttempt) {
            println("reconnect attempt $it : PrivateMessages")
        }

        on(SocketEvent.Ping) {
            println("ping : PrivateMessages")
        }

        on(SocketEvent.Pong) {
            println("pong: PrivateMessages")
        }

        on("count of unread") { data ->

            //throw RuntimeException("Data received: $data")
            println("PrivateMessages count of unread: $data")
            /*  val serializer = DeliveryCar.serializer()
              val json = JSON.nonstrict
              val deliveryCar: DeliveryCar = json.parse(serializer, data)
              //...*/
        }
    }


    val socket = Socket(
        endpoint = "https://ws.sts.pl",
        config = SocketOptions(
            queryParams = mapOf(/*"token" to "MySuperToken"*/),
            transport = SocketOptions.Transport.WEBSOCKET
        )
    ) {
        on(SocketEvent.Connect) {
            println("connect")
        }

        on(SocketEvent.Connecting) {
            println("connecting")
        }

        on(SocketEvent.Disconnect) {
            println("disconnect")
        }

        on(SocketEvent.Error) {
            println("error $it")
        }


        on(SocketEvent.Reconnect) {
            println("reconnect")
        }

        on(SocketEvent.ReconnectAttempt) {
            println("reconnect attempt $it")
        }

        on(SocketEvent.Ping) {
            println("ping")
        }

        on(SocketEvent.Pong) {
            println("pong")
        }

        on("u:odds") { data ->

            //throw RuntimeException("Data received: $data")
            println("u:oppty received: $data")
            /*  val serializer = DeliveryCar.serializer()
              val json = JSON.nonstrict
              val deliveryCar: DeliveryCar = json.parse(serializer, data)
              //...*/
        }
    }

    fun connectToTestSocket(onResponse: (String) -> Unit) {
        socket.connect()
        socket.emit("subscribe:main_opportunities", "")
        socket.emit("subscribe:offer", "")

    }

    fun connectToPrivateMessagesSocket(onResponse: (String) -> Unit) {
        privateMessagesSocket.connect()
        privateMessagesSocket.emit("token", "eyJhbGciOiJBMjU2S1ciLCJlbmMiOiJBMjU2Q0JDLUhTNTEyIn0.P-ItS8QWn67WG7urBSonKi3jZj4okti7xTmu9WRdoAfGWZdOinNHfHbHVqD7wedCmxjpBIo1UlqVFObGVEwflkRqwpmS0YiQ.JZempAVBZeeN6-MaxhIZRA.w0nm905BjZdJ2UDLVJmDhp4gmtj3HHEWsIyDnDtILbto5fSefwScCF5zvX_cIonhlUaR6FWSyqwB64fVL8xVae_6YjER4gWjiJ2m6pPoo9F65Pu5YJGjpNh8urgzVI2vhuOyca_Zug5YkaTX1an7YOIf_w2l5pfrkV_1Abafo2jgtGRDW4igOPvstSxA7dt3oBFijlpuvEYuhDfrOIvSqcW-5ZxC6G33wRV3YuoI1ZU.E4B18X162QjXKycv_0Nb0PiPNEeI4lu-N-SwSiYlnoI")
        privateMessagesSocket.emit("notification_token", "eENFRfdBZtY:APA91bFqF88HPmztHyp82MrM22WObmUQp9f8QY7i_b8TzjyJjnzIzlVitptfWR_-jzpQ2OPVhEin4dUcpOEq7X4EzLIiQiCXvl47ETkh9mgBSzMSJmdMS2tQcFn-N2I_O0-lvwrdY-r6")

        //socket.emit("subscribe:main_opportunities", "")
        //socket.emit("subscribe:offer", "")

    }
}