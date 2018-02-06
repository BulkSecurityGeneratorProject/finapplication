import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TipoContaFinapp } from './tipo-conta-finapp.model';
import { TipoContaFinappService } from './tipo-conta-finapp.service';

@Component({
    selector: 'jhi-tipo-conta-finapp-detail',
    templateUrl: './tipo-conta-finapp-detail.component.html'
})
export class TipoContaFinappDetailComponent implements OnInit, OnDestroy {

    tipoConta: TipoContaFinapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoContaService: TipoContaFinappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoContas();
    }

    load(id) {
        this.tipoContaService.find(id).subscribe((tipoConta) => {
            this.tipoConta = tipoConta;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoContas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoContaListModification',
            (response) => this.load(this.tipoConta.id)
        );
    }
}
