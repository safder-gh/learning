import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-movie-filter',
  templateUrl: './movie-filter.component.html',
  styleUrls: ['./movie-filter.component.css']
})
export class MovieFilterComponent implements OnInit {
  constructor(private formBuilder:FormBuilder){}
  ngOnInit():void{
    this.form = this.formBuilder.group(
      {
        title : '',
        genreId : 0,
        upcomingReleases : false,
        inTheaters : false,
      }
    )

    this.form.valueChanges.subscribe(values=>{
      console.log(values)
      this.movies = this.originalMovies
      this.filterMovies(values)
    })
  }
  form!:FormGroup
  movies =[
    {title:'Avatar',poster:'https://i.imgflip.com/1xh2is.jpg'},
    {title:'I Am Legend',poster:'https://image.tmdb.org/t/p/original/8WSDEBCNBReWjZe8tzthNnrofPY.jpg'},
    {title:'300',poster:'https://th.bing.com/th/id/R.17cc771232a1c57c1aef1ff6c835708a?rik=CHywvGNuF7eWMw&riu=http%3a%2f%2fis5.mzstatic.com%2fimage%2fthumb%2fVideo69%2fv4%2f87%2f39%2f3b%2f87393b8f-833e-c4ef-5531-e36a106aea3e%2fsource%2f1200x630bb.jpg&ehk=PFlUYCQYuIrpT5oUa%2f4Mm%2bj63JIR3RftUytVTlMGjmM%3d&risl=&pid=ImgRaw&r=0'},
    {title:'The Avengers',poster:'https://th.bing.com/th/id/R.c26aa21c938b764d07999a0757031188?rik=Ux52miX4DeRkuQ&pid=ImgRaw&r=0'},
  ]
  originalMovies = this.movies
  genres =[{
    id:'1',
    name:'Drama'
  },{
    id:'2',
    name:'Comedy'
  },{
    id:'3',
    name:'Action'
  }]
  filterMovies(values:any){
    if(values.title){
      this.movies = this.movies.filter(movie => movie.title.indexOf(values.title)!==-1)
    }
  }
clearForm(){
  this.form.reset()
}
}
