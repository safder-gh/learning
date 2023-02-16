import { Component,OnInit ,Input} from '@angular/core';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  ngOnInit():void {
    setTimeout(() => {
      this.movies=[{
        title :'Mission Im possible',
        releaseDate : new Date(),
        price :99.9
      },{
        title :'Mission Im possible',
        releaseDate : new Date(),
        price :99.9
      },{
        title :'Mission Im possible',
        releaseDate : new Date(),
        price :99.9
      },{
        title :'Mission Im possible',
        releaseDate : new Date(),
        price :99.9
      },];
    }, 2000);

  }
  movies;
  @Input()
  title ='Pakistani Movies';
  remove(index:number){
    console.log(index);
  }
}
