using eTickets.Data.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using System;
using System.Threading.Tasks;

namespace eTickets.Controllers
{
    public class MoviesController : Controller
    {
        private readonly IMoviesService service;
        public MoviesController(IMoviesService service)
        {
            this.service = service;
        }
        public async Task<IActionResult> Index()
        {
            var data = await service.GetAllAsync(n => n.Cinema);
            return View(data);
        }
        public async Task<IActionResult> Details(Guid Id)
        {
            var data = await service.GetByIdAsync(Id);
            return View(data);
        }

        public async Task<IActionResult> Create()
        {
            var dropDown = await service.GetMovieDropDownVM();
            ViewBag.Cinemas = new SelectList(dropDown.Cinemas, "Id", "Name");
            ViewBag.Actors = new SelectList(dropDown.Actors, "Id", "FullName");
            ViewBag.Producers = new SelectList(dropDown.Producers, "Id", "FullName");

            return View();
        }
    }
}
