import 'package:flutter/material.dart';

import 'booking.dart';
import 'login.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: ThemeData(
        primaryColor: Colors.white,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: LoginPage(),
    );
  }
}



class GridList extends StatefulWidget {

  String img;

  GridList({this.img});

  @override
  _GridListState createState() => _GridListState(img:this.img);

}

class _GridListState extends State<GridList> {
  String img;

  _GridListState({this.img});

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 2,
      child: Container(
        margin: EdgeInsets.all(10),
        width: 280,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          color: Colors.white,
        ),
        child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  Hero(tag: 'BMW X4',child: Material(child: Text('BMW X4 Sports',style: TextStyle(fontWeight:FontWeight.w700),))),
                  SizedBox(width: 90,),
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
              Text('2019 • COMFORT CLASS',style: TextStyle(fontSize: 10,color: Colors.black38),),
              SizedBox(height: 10,),
              Row(
                children: [
                  Image.asset('images/seat_icon.png',width: 18,),
                  Text(' 5 '),
                  Image.asset('images/gate_icon.png',width: 18),
                  Text(' 4 '),
                  Image.asset('images/bag_icon.png',width: 18),
                  Text(' 3 '),
                ],
              ),
              SizedBox(height: 30,),
              Hero(tag: 'bmw',child: Image.asset(img,height: 130,width: 300)),
              SizedBox(height: 20,),
              Row(
                children: [
                  Hero(tag: '210',child: Material(child: Text(r'$210',style: TextStyle(color: Colors.blue,fontSize: 20),))),
                  Text(r'per day',style: TextStyle(color: Colors.blue,fontSize: 10),),
                  SizedBox(width: 100,),
                  RaisedButton(
                    onPressed: (){
                      Navigator.of(context).push(PageRouteBuilder(pageBuilder: (context,animation,second){
                        return BooKingPage();
                      },
                        transitionDuration: Duration(milliseconds: 700),
                        reverseTransitionDuration: Duration(milliseconds: 700),
                      ));
                    },
                    color: Colors.blue,
                    shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.all(Radius.circular(10))
                    ),
                    child: Text('Book Now',style: TextStyle(color: Colors.white),
                    ),
                  ),
                ],
              ),
            ]
        ),
      ),
    );
  }


}


