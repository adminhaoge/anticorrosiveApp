import 'package:flutter/material.dart';
import 'explore.dart';

class AppBarPage extends StatelessWidget {
  final int _currentIndex = 0;
  final List items = [
      '首页','热点','车展','我的'
  ];
  final List icons = [
      Icon(Icons.ac_unit_outlined),
      Icon(Icons.shopping_cart),
      Icon(Icons.car_repair),
      Icon(Icons.home),
  ];
  final List _listPage = [
      ExplorePage()
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Explore'),
        actions: [
          IconButton(icon: Image.asset('images/user_img.png',fit: BoxFit.cover,width: 25,), onPressed: (){})
        ],
        elevation: 0,
      ),
      body: _listPage[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        unselectedItemColor: Colors.black,
        selectedItemColor: Colors.red,
        currentIndex: _currentIndex,
        type: BottomNavigationBarType.fixed,
        items: [
          _getBottomNv(0),
          _getBottomNv(1),
          _getBottomNv(2),
          _getBottomNv(3),
        ],
      ),
    );
  }
  BottomNavigationBarItem _getBottomNv(int index){
    return BottomNavigationBarItem(
        icon: this.icons[index],
        title: Text(this.items[index]),
    );
  }
}
