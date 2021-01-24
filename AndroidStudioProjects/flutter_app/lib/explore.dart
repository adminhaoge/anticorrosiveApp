import 'package:flutter/material.dart';
import 'package:flutter_app/booking.dart';
import 'main.dart';
class ExplorePage extends StatefulWidget {
  @override
  _ExplorePageState createState() => _ExplorePageState();
}

class _ExplorePageState extends State<ExplorePage> {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 5,
      initialIndex: 0,
      child: Scaffold(
        appBar: AppBar(
          actions: [
            IconButton(icon: Image.asset('images/filter_icon.png',width: 30,), onPressed: (){})
          ],
          title: Container(
            decoration: BoxDecoration(
              color: Colors.grey[200],
              borderRadius: BorderRadius.all(Radius.circular(10))
            ),
            child: TextField(
              autofocus: true,
              decoration: InputDecoration(
                  border: InputBorder.none,
                  hintText: 'What transport do you need?',
                  hintStyle: TextStyle(fontSize: 12,color: Colors.black12),
                  prefixIcon: Icon(Icons.search),
              ),
            ),
          ),
          bottom: TabBar(
            isScrollable: true,
            tabs: [
              Tab(text: 'EXPLORE',),
              Tab(text: 'CARS',),
              Tab(text: 'TRUCKS',),
              Tab(text: 'SCOOTERS',),
              Tab(text: 'HELICOPTE',),
            ],
          ),
        ),
        body: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                  height: 340,
                  margin: EdgeInsets.all(10),
                  child: ListView(
                    scrollDirection: Axis.horizontal,
                    children: [
                      GridList(img:'images/bmw_car_img.png',),
                      ListImage(img: 'images/bmw_car_img_black.png',),
                      ListImage(img: 'images/bmw_car_img_red.jpg',),
                      ListImage(img: 'images/bmw_car_img_sliver.jpg',)
                    ],
                  ),
                ),
                Container(
                    alignment: Alignment.topLeft,
                    margin: EdgeInsets.only(left: 15,bottom: 15),
                    child: Text('Trending Categories')
                ),
                Container(
                  height: 100,
                  child: ListView(
                    scrollDirection: Axis.horizontal,
                    children: [
                      Container(
                          width: 200,
                          margin: EdgeInsets.only(left: 15),
                          child: Image.asset('images/boats_img.png',fit: BoxFit.cover,),
                      ),
                      Container(
                        width: 200,
                        margin: EdgeInsets.only(left: 15),
                        child: Image.asset('images/car_img.png',fit: BoxFit.cover),
                      ),
                    ],
                  ),
                ),
                SizedBox(height: 15,),
                Container(
                  height: 100,
                  child: ListView(
                    scrollDirection: Axis.horizontal,
                    children: [
                      Container(
                        width: 200,
                        margin: EdgeInsets.only(left: 15),
                        child: Image.asset('images/wedding_car_img.png',fit: BoxFit.cover,),
                      ),
                      Container(
                        width: 200,
                        margin: EdgeInsets.only(left: 15),
                        child: Image.asset('images/show_bikes_img.png',fit: BoxFit.cover),
                      ),
                    ],
                  ),
                ),
                SizedBox(height: 15,),
                Row(
                  children: [
                    Container(
                        margin: EdgeInsets.only(left: 15),
                        child: Text('Recently viewed')),
                    Expanded(child: Text('')),
                    Container(
                        margin: EdgeInsets.only(right: 15),
                        child: Text('See All',style: TextStyle(color: Colors.blue),))
                  ],
                ),
                SizedBox(height: 15,),
                Container(
                    alignment: Alignment.topLeft,
                    margin: EdgeInsets.only(left: 15),
                    child: Text('We show you latest search results',
                      style: TextStyle(fontSize: 10,color: Colors.black38),
                    )
                ),
                SizedBox(height: 15,),
                Column(
                  children: [
                    Container(
                      height: 220,
                      margin: EdgeInsets.only(left:15),
                      child: ListView(
                        scrollDirection: Axis.horizontal,
                        children: [
                          Container(
                            width: 180,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.all(Radius.circular(10)),
                            ),
                            child: Card(
                              child: Stack(
                                  children: [
                                    Image.asset('images/bmw_m5_img.png',fit: BoxFit.cover,),
                                    Row(
                                      children: [
                                        Align(
                                          alignment: Alignment.centerLeft,
                                          child: Container(
                                            width: 35,
                                            margin: EdgeInsets.only(left: 10,bottom: 15),
                                            child: CircleAvatar(backgroundImage: AssetImage('images/bmw_user_img.png'),
                                            ),
                                          ),
                                        ),
                                        Expanded(child: Text('')),
                                        Container(
                                          alignment: Alignment.centerRight,
                                          margin: EdgeInsets.only(right: 10,bottom: 15),
                                          child: ButtonTheme(
                                            minWidth: 20,
                                            height: 30,
                                            child: RaisedButton(
                                              padding: EdgeInsets.all(6),
                                              onPressed: (){},
                                              child: Text(r'$200/day',
                                                style: TextStyle(color: Colors.white,fontSize: 12),
                                              ),
                                              shape: RoundedRectangleBorder(
                                                  borderRadius: BorderRadius.all(Radius.circular(10))
                                              ),
                                              color: Colors.blue,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                    Container(
                                      margin: EdgeInsets.only(top: 130,left: 10),
                                        child: Text('BMW M5 G-Power',
                                          style: TextStyle(fontWeight: FontWeight.w700,fontSize: 12),)
                                    ),
                                    Container(
                                        margin: EdgeInsets.only(top: 150,left: 10),
                                        child: Text('Tel-Aviv,lsrael',
                                          style: TextStyle(fontSize: 10,color: Colors.black38),)
                                    ),
                                    Row(
                                      children: [
                                        Container(
                                            width: 30,
                                            margin: EdgeInsets.only(top: 170,left: 10),
                                            child: Image.asset('images/bookmark.png')),
                                        Container(
                                            margin: EdgeInsets.only(top: 170,left: 10),
                                            child: Icon(Icons.trending_up)
                                        ),
                                        Expanded(child: Text('')),
                                        Container(
                                            margin: EdgeInsets.only(top: 170,right: 10),
                                            child: Icon(Icons.more_horiz))

                                      ],
                                    ),
                                  ],
                              ),
                            ),
                          ),
                          SizedBox(width: 15,),
                          Container(
                            width: 180,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.all(Radius.circular(10)),
                            ),
                            child: Card(
                              child: Stack(
                                children: [
                                  Image.asset('images/ford_mustang.png',fit: BoxFit.cover,),
                                  Row(
                                    children: [
                                      Align(
                                        alignment: Alignment.centerLeft,
                                        child: Container(
                                          width: 35,
                                          margin: EdgeInsets.only(left: 10,bottom: 15),
                                          child: CircleAvatar(backgroundImage: AssetImage('images/ford_user_img.png'),
                                          ),
                                        ),
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                        alignment: Alignment.centerRight,
                                        margin: EdgeInsets.only(right: 10,bottom: 15),
                                        child: ButtonTheme(
                                          minWidth: 20,
                                          height: 30,
                                          child: RaisedButton(
                                            padding: EdgeInsets.all(6),
                                            onPressed: (){},
                                            child: Text(r'$200/day',
                                              style: TextStyle(color: Colors.white,fontSize: 12),
                                            ),
                                            shape: RoundedRectangleBorder(
                                                borderRadius: BorderRadius.all(Radius.circular(10))
                                            ),
                                            color: Colors.blue,
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 130,left: 10),
                                      child: Text('BMW M5 G-Power',
                                        style: TextStyle(fontWeight: FontWeight.w700,fontSize: 12),)
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 150,left: 10),
                                      child: Text('Tel-Aviv,lsrael',
                                        style: TextStyle(fontSize: 10,color: Colors.black38),)
                                  ),
                                  Row(
                                    children: [
                                      Container(
                                          width: 30,
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Image.asset('images/bookmark.png')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Icon(Icons.trending_up)
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,right: 10),
                                          child: Icon(Icons.more_horiz))

                                    ],
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),




                    Container(
                      height: 220,
                      margin: EdgeInsets.only(left:15),
                      child: ListView(
                        scrollDirection: Axis.horizontal,
                        children: [
                          Container(
                            width: 180,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.all(Radius.circular(10)),
                            ),
                            child: Card(
                              child: Stack(
                                children: [
                                  Image.asset('images/audi.png',fit: BoxFit.cover,),
                                  Row(
                                    children: [
                                      Align(
                                        alignment: Alignment.centerLeft,
                                        child: Container(
                                          width: 35,
                                          margin: EdgeInsets.only(left: 10,bottom: 15),
                                          child: CircleAvatar(backgroundImage: AssetImage('images/audi_user_img.png'),
                                          ),
                                        ),
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                        alignment: Alignment.centerRight,
                                        margin: EdgeInsets.only(right: 10,bottom: 15),
                                        child: ButtonTheme(
                                          minWidth: 20,
                                          height: 30,
                                          child: RaisedButton(
                                            padding: EdgeInsets.all(6),
                                            onPressed: (){},
                                            child: Text(r'$200/day',
                                              style: TextStyle(color: Colors.white,fontSize: 12),
                                            ),
                                            shape: RoundedRectangleBorder(
                                                borderRadius: BorderRadius.all(Radius.circular(10))
                                            ),
                                            color: Colors.blue,
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 130,left: 10),
                                      child: Text('BMW M5 G-Power',
                                        style: TextStyle(fontWeight: FontWeight.w700,fontSize: 12),)
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 150,left: 10),
                                      child: Text('Tel-Aviv,lsrael',
                                        style: TextStyle(fontSize: 10,color: Colors.black38),)
                                  ),
                                  Row(
                                    children: [
                                      Container(
                                          width: 30,
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Image.asset('images/bookmark.png')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Icon(Icons.trending_up)
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,right: 10),
                                          child: Icon(Icons.more_horiz))

                                    ],
                                  ),
                                ],
                              ),
                            ),
                          ),
                          SizedBox(width: 15,),
                          Container(
                            width: 180,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.all(Radius.circular(10)),
                            ),
                            child: Card(
                              child: Stack(
                                children: [
                                  Image.asset('images/mercedes.png',fit: BoxFit.cover,),
                                  Row(
                                    children: [
                                      Align(
                                        alignment: Alignment.centerLeft,
                                        child: Container(
                                          width: 35,
                                          margin: EdgeInsets.only(left: 10,bottom: 15),
                                          child: CircleAvatar(backgroundImage: AssetImage('images/mercedes_user_img.png'),
                                          ),
                                        ),
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                        alignment: Alignment.centerRight,
                                        margin: EdgeInsets.only(right: 10,bottom: 15),
                                        child: ButtonTheme(
                                          minWidth: 20,
                                          height: 30,
                                          child: RaisedButton(
                                            padding: EdgeInsets.all(6),
                                            onPressed: (){},
                                            child: Text(r'$200/day',
                                              style: TextStyle(color: Colors.white,fontSize: 12),
                                            ),
                                            shape: RoundedRectangleBorder(
                                                borderRadius: BorderRadius.all(Radius.circular(10))
                                            ),
                                            color: Colors.blue,
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 130,left: 10),
                                      child: Text('BMW M5 G-Power',
                                        style: TextStyle(fontWeight: FontWeight.w700,fontSize: 12),)
                                  ),
                                  Container(
                                      margin: EdgeInsets.only(top: 150,left: 10),
                                      child: Text('Tel-Aviv,lsrael',
                                        style: TextStyle(fontSize: 10,color: Colors.black38),)
                                  ),
                                  Row(
                                    children: [
                                      Container(
                                          width: 30,
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Image.asset('images/bookmark.png')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,left: 10),
                                          child: Icon(Icons.trending_up)
                                      ),
                                      Expanded(child: Text('')),
                                      Container(
                                          margin: EdgeInsets.only(top: 170,right: 10),
                                          child: Icon(Icons.more_horiz))

                                    ],
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ],
                ),
              ],
            ),
        )
      ),
    );
  }
}




class ListImage extends StatefulWidget {

  String img;

  ListImage({this.img});

  @override
  _ListImageState createState() => _ListImageState(img:this.img);

}

class _ListImageState extends State<ListImage> {
  String img;

  _ListImageState({this.img});

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
                  Text('BMW X4 Sports',style: TextStyle(fontWeight:FontWeight.w700),),
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
              Container(child: Image.asset(img,height: 130,width: 300)),
              SizedBox(height: 20,),
              Row(
                children: [
                  Text(r'$210',style: TextStyle(color: Colors.blue,fontSize: 20),),
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
