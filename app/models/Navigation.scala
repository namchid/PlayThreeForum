package models

import scala.xml.Node

object Navigation {

  val aboutNavigation = {
    <header>ThreeForum</header>
    <div id='cssmenu'>
      <ul>
        <li class='has-sub'>
          <a href='/profile'><span>My Profile</span></a>
          <ul>
            <li><a href='/logout'>Logout</a></li>
          </ul>
        </li>
        <li>
          <a href='/forum'><span>Forum</span></a>
        </li>
        <li class='active last'><a href='/about'><span class='current'>About ThreeForum</span></a></li>
      </ul>
    </div>
  }

  val forumNavigation = {
    <header>ThreeForum</header>
    <div id='cssmenu'>
      <ul>
        <li class='has-sub'>
          <a href='/profile'><span>My Profile</span></a>
          <ul>
            <li><a href='/logout'>Logout</a></li>
          </ul>
        </li>
        <li class='active'>
          <a href='/forum'><span class='current'>Forum</span></a>
        </li>
        <li class='last'><a href='/about'>About ThreeForum</a></li>
      </ul>
    </div>
  }

  val profileNavigation = {
    <header>ThreeForum</header>
    <div id="cssmenu">
      <ul>
        <li class='active has-sub'>
          <a href='/profile'><span class='current'>My Profile</span></a>
          <ul>
            <li><a href='/logout'>Logout</a></li>
          </ul>
        </li>
        <li><a href='/forum'><span>Forum</span></a> </li>
        <li class='last'><a href='/about'><span>About ThreeForum</span></a></li>
      </ul>
    </div>
  }
  
  val errorScript: Node = {
   <script src='@routes.Assets.at("javascripts/matrix.js")' type='text/javascript'></script>   
  }
}