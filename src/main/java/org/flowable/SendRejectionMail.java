package org.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectionMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        // 触发执行逻辑
        System.err.println("你的请假申请被拒了，哈哈哈哈！");
    }
}
