using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.ModelBinding;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using MoviesAPI.DB;
using MoviesAPI.DTOs;
using MoviesAPI.Entities;
using MoviesAPI.Filters;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace MoviesAPI.Controllers
{
    [Route("api/genres")]
    [ApiController]
    public class GenresController : Controller
    {
        private readonly ApplicationDbContext context;
        private readonly IMapper mapper;

        public GenresController(ApplicationDbContext  context,IMapper mapper)
        {
                this.context = context;
            this.mapper = mapper;
        }
        public IActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public async Task<List<GenreDTO>> get()
        {
            var genres = await context.Genres.OrderBy(x => x.name).ToListAsync();
            //var genresDTOs = new List<GenreDTO>();
            //foreach(var genre in genres)
            //{
            //    genresDTOs.Add(new GenreDTO { Id = genre.Id });
            //}
            //return genresDTOs;
            return mapper.Map<List<GenreDTO>>(genres);
        }
        [HttpGet("{id:int}",Name ="getGenre")]
        public async Task<ActionResult<GenreDTO>> get(int id)
        {
            var genre = await context.Genres.FirstOrDefaultAsync(x => x.Id == id);
            if (genre == null) return NotFound();
            return mapper.Map<GenreDTO>(genre);
        }
        [HttpPost]
        public async Task<ActionResult> post([FromBody]GenreDTO genreDTO) {
            var genre = mapper.Map<Genre>(genreDTO);
            context.Genres.Add(genre);
            await context.SaveChangesAsync();
            return NoContent();
        }
        [HttpPut("{id:int}")]
        public async Task<ActionResult> put(int id,[FromBody] GenreDTO  genreDTO) {
            var genre = Mapper.Map<Genre>(genreDTO);
            genre.Id = id;
            context.Entry(genre).State = EntityState.Modified;
            await context.SaveChangesAsync();
            return NoContent();

        }
        [HttpDelete]
        public void delete() {
            throw new NotImplementedException();
        }
    }
}
