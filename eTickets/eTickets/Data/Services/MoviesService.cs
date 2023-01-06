using eTickets.Data.Base;
using eTickets.Data.ViewModels;
using eTickets.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace eTickets.Data.Services
{
    public class MoviesService : EntityBaseRepository<Movie>, IMoviesService
    {
        private readonly AppDbContext appDbContext;
        public MoviesService(AppDbContext appDbContext) : base(appDbContext)
        {
            this.appDbContext = appDbContext;
        }

        public Task AddNewMovie(MovieVM movieVM)
        {
            var movie = new Movie()
            {
                Id = Guid.NewGuid(),
                Name = movieVM.Name,
                Description = movieVM.Description,
                Price = movieVM.Price,
                ImageURL = movieVM.ImageURL,
                CinemaId = movieVM.CinemaId,
                StartDate = movieVM.StartDate,
                EndDate = movieVM.EndDate,
                MovieCategory = movieVM.MovieCategory,
                ProducerId = movieVM.ProducerId,
            };
            appDbContext.Movies.AddAsync(movie);
        }

        public new async Task<Movie> GetByIdAsync(Guid Id)
        {
            var movie = appDbContext.Movies
                .Include(c => c.Cinema)
                .Include(p => p.Producer)
                .Include(am => am.ActoreMovies).ThenInclude(a => a.Actor)
                .FirstOrDefaultAsync(n => n.Id == Id);
            return await movie;
        }

        public async Task<MovieDropDownVM> GetMovieDropDownVM()
        {
            var dropDowns = new MovieDropDownVM()
            {
                Actors = await appDbContext.Actors.OrderBy(n => n.FirstName).ToListAsync(),
                Cinemas = await appDbContext.Cinemas.OrderBy(n => n.Name).ToListAsync(),
                Producers = await appDbContext.Producers.OrderBy(n => n.FirstName).ToListAsync()

            };
            return dropDowns;
        }
    }
}
//https://github.com/etrupja