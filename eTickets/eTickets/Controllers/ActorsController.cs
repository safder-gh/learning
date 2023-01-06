using eTickets.Data.Services;
using eTickets.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;

namespace eTickets.Controllers
{
    public class ActorsController : Controller
    {
        private readonly IActorsService service;
        public ActorsController(IActorsService service)
        {
            this.service = service;
        }
        public async Task<IActionResult> Index()
        {
            var data = await service.GetAllAsync();
            return View(data);
        }

        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Create([Bind("ProfilePictureURL,FirstName,MiddleName,LastName,Bio")] Actor actor)
        {
            if (!ModelState.IsValid)
            {
                return View(actor);
            }
            else
            {
                actor.Id = new System.Guid();
                this.service.AddAsync(actor);

                return RedirectToAction("Index");
            }

        }
        public async Task<IActionResult> Details(Guid id)
        {
            var actor = await this.service.GetByIdAsync(id);
            if (actor == null)
            {
                return View("NotFound");
            }
            return View(actor);
        }
        public async Task<IActionResult> Edit(Guid id)
        {
            Actor actor = await this.service.GetByIdAsync(id);
            return View(actor);
        }
        [HttpPost]
        public async Task<IActionResult> Edit(Guid id, [Bind("Id,ProfilePictureURL,FirstName,MiddleName,LastName,Bio")] Actor actor)
        {

            if (!ModelState.IsValid)
            {
                return View(actor);
            }
            else
            {
                await this.service.UpdateAsync(actor);
                return RedirectToAction(nameof(Index));
            }

        }
        public async Task<IActionResult> Delete(Guid id)
        {
            Actor actor = await this.service.GetByIdAsync(id);
            if (actor == null)
            {
                return View("NotFound");
            }
            return View(actor);
        }
        [HttpPost, ActionName("Delete")]
        public async Task<IActionResult> DeleteConfirmed(Guid id)
        {
            Actor actor = await this.service.GetByIdAsync(id);
            if (actor == null)
            {
                return View("NotFound");
            }
            await this.service.DeleteAsync(id);
            return RedirectToAction(nameof(Index));
        }
    }
}
