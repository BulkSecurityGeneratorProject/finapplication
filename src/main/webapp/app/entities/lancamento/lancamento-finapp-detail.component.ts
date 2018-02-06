import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LancamentoFinapp } from './lancamento-finapp.model';
import { LancamentoFinappService } from './lancamento-finapp.service';

@Component({
    selector: 'jhi-lancamento-finapp-detail',
    templateUrl: './lancamento-finapp-detail.component.html'
})
export class LancamentoFinappDetailComponent implements OnInit, OnDestroy {

    lancamento: LancamentoFinapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private lancamentoService: LancamentoFinappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLancamentos();
    }

    load(id) {
        this.lancamentoService.find(id).subscribe((lancamento) => {
            this.lancamento = lancamento;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLancamentos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'lancamentoListModification',
            (response) => this.load(this.lancamento.id)
        );
    }
}
