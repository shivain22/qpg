import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { SubTopicMasterUpdateComponent } from 'app/entities/sub-topic-master/sub-topic-master-update.component';
import { SubTopicMasterService } from 'app/entities/sub-topic-master/sub-topic-master.service';
import { SubTopicMaster } from 'app/shared/model/sub-topic-master.model';

describe('Component Tests', () => {
  describe('SubTopicMaster Management Update Component', () => {
    let comp: SubTopicMasterUpdateComponent;
    let fixture: ComponentFixture<SubTopicMasterUpdateComponent>;
    let service: SubTopicMasterService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [SubTopicMasterUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SubTopicMasterUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubTopicMasterUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubTopicMasterService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubTopicMaster(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SubTopicMaster();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
