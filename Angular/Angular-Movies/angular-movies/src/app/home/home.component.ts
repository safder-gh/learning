import { Component ,OnInit} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  constructor(){

  }
  ngOnInit():void {
    this.movies=[{
      title :'Mission Im possible',
      releaseDate : new Date(),
      price :99.9,
      poster:"/assets/mi.jpg"
    },{
      title :'Moana',
      releaseDate : new Date(),
      price :99.9,
      poster:"/assets/OIP.jpg"
    },];
  }
  movies;
  handleRating(rate:number){
    alert(`user select ${rate}`)
    }
}
