import { Component,OnInit ,Input} from '@angular/core';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  ngOnInit():void {


  }
  @Input()
  movies;
  @Input()
  title ='Pakistani Movies';
  remove(index:number){
    this.movies.splice(index,1)
    console.log(index)
  }
}
