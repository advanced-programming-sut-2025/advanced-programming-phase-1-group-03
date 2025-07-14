<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.11.2" name="Objects" tilewidth="32" tileheight="32" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="2" type="Object">
  <properties>
   <property name="controller" type="bool" value="true"/>
   <property name="speed" type="float" value="3"/>
  </properties>
  <image source="player.png" width="32" height="32"/>
  <objectgroup draworder="index" id="2">
   <object id="6" x="11" y="6.5" width="9.5" height="16">
    <properties>
     <property name="speed" type="float" value="10"/>
    </properties>
   </object>
  </objectgroup>
 </tile>
</tileset>
