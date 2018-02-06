import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EntidadeReceitaDespesaFinapp } from './entidade-receita-despesa-finapp.model';
import { EntidadeReceitaDespesaFinappService } from './entidade-receita-despesa-finapp.service';

@Component({
    selector: 'jhi-entidade-receita-despesa-finapp-detail',
    templateUrl: './entidade-receita-despesa-finapp-detail.component.html'
})
export class EntidadeReceitaDespesaFinappDetailComponent implements OnInit, OnDestroy {

    entidadeReceitaDespesa: EntidadeReceitaDespesaFinapp;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entidadeReceitaDespesaService: EntidadeReceitaDespesaFinappService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntidadeReceitaDespesas();
    }

    load(id) {
        this.entidadeReceitaDespesaService.find(id).subscribe((entidadeReceitaDespesa) => {
            this.entidadeReceitaDespesa = entidadeReceitaDespesa;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntidadeReceitaDespesas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entidadeReceitaDespesaListModification',
            (response) => this.load(this.entidadeReceitaDespesa.id)
        );
    }
}
