import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ContaFinapp } from './conta-finapp.model';
import { ContaFinappService } from './conta-finapp.service';

@Component({
    selector: 'jhi-conta-finapp-detail',
    templateUrl: './conta-finapp-detail.component.html'
})
export class ContaFinappDetailComponent implements OnInit, OnDestroy {

    conta: ContaFinapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private contaService: ContaFinappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContas();
    }

    load(id) {
        this.contaService.find(id).subscribe((conta) => {
            this.conta = conta;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInContas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'contaListModification',
            (response) => this.load(this.conta.id)
        );
    }
}
