import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';

import { IndexGenresComponent } from './genres/index-genres/index-genres.component'
import { CreateGenreComponent } from './genres/create-genre/create-genre.component'
import { EditGenreComponent } from './genres/edit-genre/edit-genre.component'

import { IndexActorComponent } from './actors/index-actor/index-actor.component'
import { CreateActorComponent } from './actors/create-actor/create-actor.component';
import { EditActorComponent } from './actors/edit-actor/edit-actor.component';

import { IndexMovieTheaterComponent } from './movie-theaters/index-movie-theater/index-movie-theater.component'
import { CreateMovieTheaterComponent } from './movie-theaters/create-movie-theater/create-movie-theater.component'

import { CreateMovieComponent } from './movies/create-movie/create-movie.component'
import { MovieFilterComponent } from './movies/movie-filter/movie-filter.component';


const routes: Routes = [
  {path:'',component:HomeComponent},

  {path:'genres',component:IndexGenresComponent},
  {path:'genres/create',component:CreateGenreComponent},
  {path:'genres/edit/:id',component:EditGenreComponent},

  {path:'actors',component:IndexActorComponent},
  {path:'actors/create',component:CreateActorComponent},
  {path:'actors/edit/:id',component:EditActorComponent},

  {path:'movietheaters',component:IndexMovieTheaterComponent},
  {path:'movietheaters/create',component:CreateMovieTheaterComponent},

   {path:'movies/create',component:CreateMovieComponent},
   {path:'movies/filter',component:MovieFilterComponent},

   {path:'**',redirectTo:''},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
