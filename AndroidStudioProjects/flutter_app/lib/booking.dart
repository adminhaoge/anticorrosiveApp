import 'package:flutter/material.dart';

class BooKingPage extends StatefulWidget {
  @override
  _BooKingPageState createState() => _BooKingPageState();
}

class _BooKingPageState extends State<BooKingPage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primaryColor: Colors.white,
      ),
      home: Scaffold(
        appBar: PreferredSize(
          preferredSize: Size.fromHeight(80),
          child: AppBar(
            elevation: 0.8,
            title: Text('Booking'),
            actions: [
              IconButton(icon: Image.asset('images/user_img.png'),
                  onPressed: null,
              ),
            ],
          ),
        ),
        body: ListView(
          children: [
            Card(
              elevation: 5,
              margin: EdgeInsets.all(10),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    margin: EdgeInsets.all(10),
                    child: Row(
                      children: [
                        Hero(tag: "BMW X4",child: Material(child: Text("BMW X4 Sports ",style: TextStyle(fontSize: 16),))),
                        Expanded(child: Text('')),
                        Container(
                          height: 35,
                          width: 80,
                          child: RaisedButton.icon(
                            onPressed: (){},
                            elevation: 0.0,
                            icon: Icon(Icons.star),
                            label: Text('4.5'),
                            textColor: Colors.lime,
                            color: Colors.limeAccent,
                            shape:RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(10)
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.only(left: 8),
                    child: Text("2019·COMFORT CLASS",style: TextStyle(fontSize: 13,color: Colors.grey)),
                  ),
                  Container(
                    margin: EdgeInsets.all(6),
                    child: Row(
                      children: [
                        Image.asset('images/seat_icon.png',
                          fit: BoxFit.cover,
                          width: 18.0,
                        ),
                        Text(" 5 "),
                        Image.asset('images/gate_icon.png',
                          fit: BoxFit.cover,
                          width: 18.0,
                        ),
                        Text(" 4 "),
                        Image.asset('images/bag_icon.png',
                          fit: BoxFit.cover,
                          width: 18.0,
                        ),
                        Text(" 3 "),
                      ],
                    ),
                  ),
                  Hero(
                    tag: "bmw",
                    child: Container(
                      child: Material(color:Colors.white,child: Image.asset('images/bmw_car_img.png')),
                    ),
                  ),
                  Container(
                    child: Row(
                      children: [
                        Container(
                            margin: EdgeInsets.only(bottom: 15,top: 15,left: 15,right: 5),
                            child: Hero(tag: "210",child: Material(child: Text(r'$210 ',style: TextStyle(fontSize: 20,color: Colors.blue),)))
                        ),
                        Container(
                            margin: EdgeInsets.only(bottom: 10),
                            child: Text('per day',style: TextStyle(fontSize: 10,color: Colors.cyan),)
                        ),
                      ],
                    ),
                  )
                ],
              ),
            ),
            Card(
              child: Container(
                padding: EdgeInsets.all(10),
                child: Row(
                  children: [
                    Icon(Icons.location_on),
                    SizedBox(width: 10,),
                    Text('1560 Broadway\n'
                        'Unit 1001\n'
                        'New York,NY 10036\n'
                        'United States'),
                    Expanded(child: Text('')),
                    Card(child: Image.asset('images/ic_map.jpg',width: 150,)),
                  ],
                ),
              ),
            ),

            Card(
              child: Container(
                padding: EdgeInsets.all(10),
                child: Row(
                  children: [
                    Icon(Icons.access_time),
                    SizedBox(width: 10,),
                    Text.rich(TextSpan(
                      text: '11/12/2020\n',
                      style: TextStyle(color: Colors.lightBlueAccent),
                      children: [
                        TextSpan(
                          text: '14/12/2020',
                          style: TextStyle(color: Colors.blue)
                        ),
                      ]
                    )),
                    Expanded(child: Text('')),
                    Card(child: IconButton(icon: Icon(Icons.remove), onPressed: null)),
                    Text.rich(TextSpan(
                        text: '   3\n',
                        style: TextStyle(color: Colors.lightBlueAccent,fontWeight: FontWeight.w700),
                        children: [
                          TextSpan(
                              text: 'Days',
                            style: TextStyle(color: Colors.black),
                          ),
                        ]
                    )),
                    Card(child: IconButton(icon: Icon(Icons.add), onPressed: null)),
                  ],
                ),
              ),
            ),
            Container(
              margin: EdgeInsets.only(top: 5),
              alignment: Alignment.bottomCenter,

              height: 80,
              color: Colors.white,
              child: Row(
                children: [
                  Container(
                      padding: EdgeInsets.only(left: 10,bottom: 30),
                      child: Text(r'$630',style: TextStyle(color: Colors.blueAccent,fontSize: 30,fontWeight: FontWeight.w700),)
                  ),
                  Container(
                      padding: EdgeInsets.only(bottom: 30),
                      child: Checkbox(value: false, onChanged: null,activeColor: Colors.blueAccent,)),
                  Container(
                    padding: EdgeInsets.only(bottom: 30),
                    child: Text.rich(TextSpan(
                      text: 'Accepted',
                      children: [
                        TextSpan(
                          text: 'User Agreement',
                          style: TextStyle(
                              decoration: TextDecoration.underline,
                              color:Colors.blueAccent),
                        ),
                      ]
                    )),
                  ),
                  Container(
                    padding: EdgeInsets.only(left: 20,bottom: 30),
                    child: RaisedButton(
                      onPressed: (){},
                      color: Colors.blue,
                      shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.all(Radius.circular(10))
                      ),
                      child: Text('Pay Now',style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
