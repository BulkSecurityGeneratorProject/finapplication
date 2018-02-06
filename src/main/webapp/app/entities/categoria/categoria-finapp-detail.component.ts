import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CategoriaFinapp } from './categoria-finapp.model';
import { CategoriaFinappService } from './categoria-finapp.service';

@Component({
    selector: 'jhi-categoria-finapp-detail',
    templateUrl: './categoria-finapp-detail.component.html'
})
export class CategoriaFinappDetailComponent implements OnInit, OnDestroy {

    categoria: CategoriaFinapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categoriaService: CategoriaFinappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategorias();
    }

    load(id) {
        this.categoriaService.find(id).subscribe((categoria) => {
            this.categoria = categoria;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategorias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categoriaListModification',
            (response) => this.load(this.categoria.id)
        );
    }
}
